package fon.njt.EvidencijaZavrsnihRadovaapi.service;

import fon.njt.EvidencijaZavrsnihRadovaapi.dto.GraduateThesisDto;
import fon.njt.EvidencijaZavrsnihRadovaapi.entity.*;
import fon.njt.EvidencijaZavrsnihRadovaapi.entity.key.BoardFunctionKey;
import fon.njt.EvidencijaZavrsnihRadovaapi.exceptions.BadRequestBodyException;
import fon.njt.EvidencijaZavrsnihRadovaapi.exceptions.NotPresentException;
import fon.njt.EvidencijaZavrsnihRadovaapi.exceptions.NotificationException;
import fon.njt.EvidencijaZavrsnihRadovaapi.mapper.GraduateThesisMapper;
import fon.njt.EvidencijaZavrsnihRadovaapi.repository.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.ui.context.Theme;

import javax.transaction.Transactional;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@Transactional
@AllArgsConstructor
public class GraduateThesisService {
    private final GraduateThesisRepository graduateThesisRepository;
    private final NotificationRepository notificationRepository;
    private final StudentRepository studentRepository;
    private final BoardRepository boardRepository;
    private final BoardFunctionRepository boardFunctionRepository;
    private final DocumentRepository documentRepository;
    private final StudentService studentService;
    private final ProfessorService professorService;
    private final GraduateThesisMapper thesisMapper;
    private final AuthService authService;
    private final GraduateThesisMapper mapper;


    public List<GraduateThesis> getAllPublished() {
        return graduateThesisRepository.findAllByVisibilityStatus(VisibilityStatus.PUBLISHED).get();
    }

    public List<GraduateThesis> getAll() {
        return graduateThesisRepository.findAll();
    }

    public void save(GraduateThesisDto thesisDto) {
        graduateThesisRepository.save(thesisMapper.map(thesisDto));
    }

    public GraduateThesis getMyThesis() {
        String studentUsername = authService.getCurrentUserUsername();

        Optional<GraduateThesis> thesis = graduateThesisRepository.findByStudentUserProfileUsername(studentUsername);
        if (!thesis.isPresent()) {
            throw new NotPresentException("thesis not found");
        }
        return thesis.get();
    }


    public void processRequestTitle(Long professorId) {
        Student s = studentService.findByUsername(authService.getCurrentUser().getUsername());
        System.out.println("Prof id -----------------> " + professorId);
        Professor p = professorService.findById(professorId);
        Notification n = new Notification();
        n.setUser(p.getUserProfile());
        n.setSender(authService.getCurrentUser());
        n.setTopic("Zahtev za temu");
        n.setCreatedAt(new Date());
        n.setMessage("Student " + s.getName() + " " + s.getSurname() + " " + s.getIndexNumber() + " želi da dobije temu za diplomski rad");
        if (notificationRepository.findByUserAndSenderAndTopic(n.getUser(), n.getSender(), n.getTopic()).isPresent()) {
            throw new NotificationException("Obavestenje je vec poslato!");
        }
        n = notificationRepository.save(n);
    }

    public GraduateThesis setTitle(Map<String, Object> map) {
        try {
            String studentUsername = (String) map.get("username");
            String title = (String) map.get("title");
            Optional<GraduateThesis> thesisOpt = graduateThesisRepository.findByStudentUserProfileUsername(studentUsername);
            if (!thesisOpt.isPresent()) {
                throw new NotPresentException("thesis not found");
            }
            // Updates
            GraduateThesis thesis = thesisOpt.get();
            thesis.setTitle(title);
            thesis.setProgressStatus(ProgressStatus.IN_PROGRESS);
            if (thesis.getBoard() == null) {
                thesis.setBoard(makeBoard(thesis));
            }
            Professor professor = professorService.findByUsername(authService.getCurrentUserUsername());
            thesis.getStudent().setMentor(professor);
            thesis = graduateThesisRepository.save(thesis);
            notify(studentUsername);
            return thesis;
        } catch (Exception e) {
            e.printStackTrace();
            throw new BadRequestBodyException("Error retrieving notification or/and title data");
        }

    }

    private Board makeBoard(GraduateThesis thesis) {
        String professorUsername = authService.getCurrentUserUsername();
        Professor professor = professorService.findByUsername(professorUsername);
        BoardFunction boardFunction;
        Board board;
        boardFunction = BoardFunction.builder()
                .function("MENTOR")
                .professor(professor)
                .joinDate(new Date())
                .build();
        board = Board.builder().dateOfFormation(new Date()).numberOfMembers(1)
                .addProfessor(boardFunction).build();
        board = boardRepository.save(board);
        boardFunction.setBoard(board);
        boardFunction.setBoardFunctionId(new BoardFunctionKey(professor.getPersonId(), board.getBoardId()));
        boardFunction = boardFunctionRepository.save(boardFunction);
        return board;
    }

    private void notify(String username) {
        Notification n = new Notification();
        n.setUser(studentService.findByUsername(username).getUserProfile());
        n.setSender(authService.getCurrentUser());
        n.setMessage("Dobili ste temu za diplomski rad!");
        n.setTopic("Tema");
        n.setCreatedAt(new Date());
        notificationRepository.save(n);
    }

    public GraduateThesis getThesisByStudentId(Long studentId) {
        Optional<GraduateThesis> thesis = graduateThesisRepository.findByStudentPersonId(studentId);
        if (!thesis.isPresent()) {
            throw new NotPresentException("thesis not found");
        }
        return thesis.get();

    }

    public GraduateThesis getThesisByBoardId(Long boardId) {
        Optional<GraduateThesis> thesis = graduateThesisRepository.findByBoardBoardId(boardId);
        if (!thesis.isPresent()) {
            throw new NotPresentException("theses not found");
        }
        return thesis.get();
    }


    public GraduateThesis updateThesis(GraduateThesisDto dto) {
        GraduateThesis graduateThesis = graduateThesisRepository.findById(dto.getGraduateThesisId()).orElse(null);
        graduateThesis = mapper.updateThesisFromDto(dto, graduateThesis);
        graduateThesisRepository.save(graduateThesis);
        return graduateThesis;
    }


    public GraduateThesis updateThesis(GraduateThesisDto dto, Long id) {
        GraduateThesis graduateThesis = graduateThesisRepository.findById(id).orElse(null);
        graduateThesis = mapper.updateThesisFromDto(dto, graduateThesis);
        graduateThesisRepository.save(graduateThesis);
        return graduateThesis;
    }

    public GraduateThesis publish(GraduateThesisDto thesisDto) {
        GraduateThesis thesis = updateThesis(thesisDto, thesisDto.getGraduateThesisId());
        Optional<List<Document>> documents = documentRepository.findByFinalThesisGraduateThesisId(thesis.getGraduateThesisId());
        if (!documents.isPresent()) {
            throw new NotPresentException("documents not found");
        }
        List<Document> docs = documents.get();
        docs.stream().forEach(document -> {
            document.setStatus(VisibilityStatus.PUBLISHED);
            documentRepository.save(document);
        });
        return thesis;
    }

    public GraduateThesis unpublish(GraduateThesisDto thesisDto) {
        GraduateThesis thesis = updateThesis(thesisDto, thesisDto.getGraduateThesisId());
        Optional<List<Document>> documents = documentRepository.findByFinalThesisGraduateThesisId(thesis.getGraduateThesisId());
        if (!documents.isPresent()) {
            throw new NotPresentException("documents not found");
        }
        List<Document> docs = documents.get();
        docs.stream().map(document -> {
            document.setStatus(VisibilityStatus.PRIVATE);
            documentRepository.save(document);
            return document;
        }).collect(Collectors.toList());

        return thesis;
    }

    public List<GraduateThesisDto> getThesesByYear(int year) {
        try {
            String startDateString = (year - 1) + "-10-01";
            String endDateString = (year) + "-10-01";
            Date startDate = new SimpleDateFormat("yyyy-MM-dd").parse(startDateString);
            Date endDate = new SimpleDateFormat("yyyy-MM-dd").parse(endDateString);
            List<GraduateThesis> graduateTheses = graduateThesisRepository.findByDateOfReceptionBetween(startDate, endDate).orElse(null);
            graduateTheses.forEach(System.out::println);
            return graduateTheses.stream().map(mapper::map).collect(Collectors.toList());
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Greska prilikom ucitavanja zavrsnih radova po datumu");
        }
    }

    public GraduateThesisDto getThesis(Long id) {
        return mapper.map(graduateThesisRepository.findById(id).orElse(null));
    }

    public GraduateThesis getThesisByStudentUsername(String studentUsername) {
        Optional<GraduateThesis> thesis = graduateThesisRepository.findByStudentUserProfileUsername(studentUsername);
        if (!thesis.isPresent()) {
            throw new NotPresentException("thesis not found");
        }
        return thesis.get();
    }
}
