package fon.njt.EvidencijaZavrsnihRadovaapi.service;

import fon.njt.EvidencijaZavrsnihRadovaapi.entity.*;
import fon.njt.EvidencijaZavrsnihRadovaapi.exceptions.NotPresentException;
import fon.njt.EvidencijaZavrsnihRadovaapi.repository.DocumentRepository;
import fon.njt.EvidencijaZavrsnihRadovaapi.repository.DocumentTypeRepository;
import fon.njt.EvidencijaZavrsnihRadovaapi.repository.ProfessorRepository;
import fon.njt.EvidencijaZavrsnihRadovaapi.repository.StudentRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class DocumentService {
    private final FileStorageService storageService;
    private final StudentRepository studentRepository;
    private final ProfessorRepository professorRepository;
    private final GraduateThesisService thesisService;
    private final AuthService authService;
    private final DocumentRepository documentRepository;
    private final DocumentTypeRepository documentTypeRepository;


    public void save(MultipartFile file, String type) {
        Optional<Student> os = studentRepository.findByUserProfileUsername(authService.getCurrentUserUsername());
        if (!os.isPresent())
            throw new NotPresentException("student not found");
        Student s = os.get();
        String folderName = getFolderName(s);
        String fileType = getExtensionByStringHandling(file.getOriginalFilename()).isPresent() ? getExtensionByStringHandling(file.getOriginalFilename()).get() : "";
        storageService.save(file, folderName);
        Document document = Document.builder()
                .name(file.getOriginalFilename())
                .status(VisibilityStatus.PRIVATE)
                .fileLocation(folderName)
                .finalThesis(thesisService.getThesisByStudentId(s.getPersonId()))
                .documentType(documentTypeRepository.getByName(type))
                .fileType(fileType)
                .build();
        documentRepository.save(document);

    }

    public Optional<String> getExtensionByStringHandling(String filename) {
        return Optional.ofNullable(filename)
                .filter(f -> f.contains("."))
                .map(f -> f.substring(filename.lastIndexOf(".") + 1));
    }


    private String getFolderName(Student s) {
        String folderName = s.getIndexNumber();
        String[] indexNum = folderName.split("/");
        folderName = indexNum[0] + indexNum[1];
        System.out.println(folderName);
        return folderName;
    }

    @Transactional
    public int delete(String folderName, String filename) {
        return documentRepository.deleteByFileLocationAndName(folderName, filename);
    }

    public Document updateVisibilityStatus(String folderName, String filename, String status) {
        Optional<Document> doc = documentRepository.findByFileLocationAndName(folderName, filename);
        if (!doc.isPresent()) {
            throw new NotPresentException("Document not found!");
        }
        Document document = doc.get();
        document.setStatus(VisibilityStatus.valueOf(status));
        return documentRepository.save(document);
    }

    public Document findByLocationAndName(String folderName, String filename) {
        Optional<Document> doc = documentRepository.findByFileLocationAndName(folderName, filename);
        if (!doc.isPresent()) {
            throw new NotPresentException("Document not found!");
        }
        return doc.get();

    }

    public List<Document> getDocuments(String folderName) {
        List<Document> allDocs = getDocumentsByFileLocation(folderName);
        List<Document> docs = new LinkedList<>();
        for (Document d : allDocs) {
            switch (d.getStatus()) {
                case PUBLISHED:
                    docs.add(d);
                    break;
                case BOARD_VIEW:
                case PRIVATE:
                    if (authService.isLoggedIn() && isAuthorized(d.getStatus(), d)) {
                        docs.add(d);
                    }
                    break;
                default:
                    break;
            }
        }
        return docs;
    }

    private boolean isAuthorized(VisibilityStatus visibilityStatus, Document d) {
        UserProfile user = authService.getCurrentUser();
        d.getFinalThesis().getStudent().getMentor();
        Board b = d.getFinalThesis().getBoard();
        switch (user.getRole().getName()) {
            case "STUDENT":
                Student s = studentRepository.findByUserProfileUsername(user.getUsername()).get();
                if (visibilityStatus == VisibilityStatus.PUBLISHED) {
                    return true;
                } else {
                    return d.getFinalThesis().getStudent().equals(s);
                }
            case "PROFESSOR":
                List<Professor> profs = d.getFinalThesis().getBoard().getProfessors().stream().map(BoardFunction::getProfessor).collect(Collectors.toList());
                Professor p = professorRepository.findByUserProfileUsername(user.getUsername()).get();
                if (visibilityStatus != VisibilityStatus.PRIVATE) {
                    return profs.contains(p);
                } else {
                    return p.equals(d.getFinalThesis().getStudent().getMentor());
                }
            default:
                return false;
        }
    }

    private List<Document> getDocumentsByFileLocation(String folderName) {
        Optional<List<Document>> docs = documentRepository.findByFileLocation(folderName);
        if (!docs.isPresent()) {
            throw new NotPresentException("Document not found!");
        }
        return docs.get();
    }
}
