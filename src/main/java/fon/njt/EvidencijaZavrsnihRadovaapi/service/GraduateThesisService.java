package fon.njt.EvidencijaZavrsnihRadovaapi.service;

import fon.njt.EvidencijaZavrsnihRadovaapi.dto.GraduateThesisDto;
import fon.njt.EvidencijaZavrsnihRadovaapi.dto.ThesisDto;
import fon.njt.EvidencijaZavrsnihRadovaapi.entity.*;
import fon.njt.EvidencijaZavrsnihRadovaapi.entity.key.BoardFunctionKey;
import fon.njt.EvidencijaZavrsnihRadovaapi.exceptions.BadRequestBodyException;
import fon.njt.EvidencijaZavrsnihRadovaapi.exceptions.NotPresentException;
import fon.njt.EvidencijaZavrsnihRadovaapi.exceptions.NotificationException;
import fon.njt.EvidencijaZavrsnihRadovaapi.mapper.GraduateThesisMapper;
import fon.njt.EvidencijaZavrsnihRadovaapi.repository.BoardFunctionRepository;
import fon.njt.EvidencijaZavrsnihRadovaapi.repository.BoardRepository;
import fon.njt.EvidencijaZavrsnihRadovaapi.repository.GraduateThesisRepository;
import fon.njt.EvidencijaZavrsnihRadovaapi.repository.NotificationRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;


@Service
@AllArgsConstructor
public class GraduateThesisService {
    private final GraduateThesisRepository graduateThesisRepository;
    private final NotificationRepository notificationRepository;
    private final BoardRepository boardRepository;
    private final BoardFunctionRepository boardFunctionRepository;
    private final StudentService studentService;
    private final ProfessorService professorService;
    private final GraduateThesisMapper thesisMapper;
    private final AuthService authService;


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

    public void setTitle(Map<String, Object> map) {
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
            graduateThesisRepository.save(thesis);
            notify(studentUsername);
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

    public GraduateThesis getThesis(String studentId) {
        Optional<GraduateThesis> thesis = graduateThesisRepository.findByStudentPersonId(Long.parseLong(studentId));
        if (!thesis.isPresent()) {
            throw new NotPresentException("thesis not found");
        }
        return thesis.get();

    }
}
