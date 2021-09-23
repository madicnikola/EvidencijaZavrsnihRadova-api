package fon.njt.EvidencijaZavrsnihRadovaapi.service;

import fon.njt.EvidencijaZavrsnihRadovaapi.dto.StudentDto;
import fon.njt.EvidencijaZavrsnihRadovaapi.entity.BoardFunction;
import fon.njt.EvidencijaZavrsnihRadovaapi.entity.GraduateThesis;
import fon.njt.EvidencijaZavrsnihRadovaapi.entity.Professor;
import fon.njt.EvidencijaZavrsnihRadovaapi.entity.Student;
import fon.njt.EvidencijaZavrsnihRadovaapi.exceptions.NotPresentException;
import fon.njt.EvidencijaZavrsnihRadovaapi.mapper.StudentMapper;
import fon.njt.EvidencijaZavrsnihRadovaapi.repository.GraduateThesisRepository;
import fon.njt.EvidencijaZavrsnihRadovaapi.repository.StudentRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class StudentService {
    private final StudentRepository studentRepository;
    private final GraduateThesisRepository thesisRepository;
    private final ProfessorService professorService;
    private final BoardService boardService;
    private final BoardFunctionService boardFunctionService;
    private final AuthService authService;
    private final StudentMapper mapper;

    public Student findByUsername(String username) {
        Optional<Student> s = studentRepository.findByUserProfileUsername(username);
        if (!s.isPresent()) {
            throw new NotPresentException("Student not found");
        }
        return s.get();
    }

    public Student getByUsername() {
        String studentUsername = authService.getCurrentUserUsername();
        Optional<Student> s = studentRepository.findByUserProfileUsername(studentUsername);
        if (!s.isPresent()) {
            throw new NotPresentException("Student not found");
        }
        return s.get();
    }

    public List<StudentDto> getMyStudents() {
        String profUsername = authService.getCurrentUserUsername();
        Professor professor = professorService.findByUsername(profUsername);
        Optional<List<Student>> students = studentRepository.findByMentorPersonId(professor.getPersonId());
        if (!students.isPresent()) {
            throw new NotPresentException("Students not found");
        }
        return students.get().stream().map(mapper::map).collect(Collectors.toList());
    }

    public List<StudentDto> getStudentsByBoardMember(String boardMemberUsername) {
        List<StudentDto> studentDtos = new ArrayList<>();
        Professor professor = professorService.findByUsername(boardMemberUsername);
        List<BoardFunction> board_functions = boardFunctionService.findByFunctionAndProfessorId("BOARD_MEMBER", professor.getPersonId());
        board_functions.forEach(boardFunction -> {
            GraduateThesis graduateThesis = getThesisByBoardId(boardFunction.getBoard().getBoardId());
            studentDtos.add(mapper.map(graduateThesis.getStudent()));
        });
        return studentDtos;
    }


    public List<StudentDto> getAllStudents() {
        return studentRepository.findAll().stream().map(mapper::map).collect(Collectors.toList());
    }


    private GraduateThesis getThesisByBoardId(Long boardId) {
        Optional<GraduateThesis> thesis = thesisRepository.findByBoardBoardId(boardId);
        if (!thesis.isPresent()) {
            throw new NotPresentException("theses not found");
        }
        return thesis.get();
    }

    public List<StudentDto> getStudentsByMentorUsername(String mentorUsername) {
        Optional<List<Student>> students = studentRepository.findByMentorUserProfileUsername(mentorUsername);
        if (!students.isPresent()) {
            throw new NotPresentException("Students not found");
        }
        return students.get().stream().map(mapper::map).collect(Collectors.toList());
    }
}


//        List<Long> boardIds = new ArrayList<>();
//        professor.getBoardFunctionsList().stream().map(boardFunction ->
//                boardIds.add(boardFunction.getBoardFunctionId().getBoardId()));

//        studentRepository
