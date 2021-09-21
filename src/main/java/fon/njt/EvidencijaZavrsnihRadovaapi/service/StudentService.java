package fon.njt.EvidencijaZavrsnihRadovaapi.service;

import fon.njt.EvidencijaZavrsnihRadovaapi.dto.StudentDto;
import fon.njt.EvidencijaZavrsnihRadovaapi.entity.GraduateThesis;
import fon.njt.EvidencijaZavrsnihRadovaapi.entity.Professor;
import fon.njt.EvidencijaZavrsnihRadovaapi.entity.Student;
import fon.njt.EvidencijaZavrsnihRadovaapi.exceptions.NotPresentException;
import fon.njt.EvidencijaZavrsnihRadovaapi.mapper.StudentMapper;
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
    private final ProfessorService professorService;
    private final BoardService boardService;
    private final AuthService authService;
    private final StudentMapper mapper;

    public Student findByUsername(String username) {
        Optional<Student> s = studentRepository.findByUserProfileUsername(username);
        if (!s.isPresent()) {
            throw new NotPresentException("Student not found");
        }
        return s.get();
    }

    public Student getCurrent() {
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
}


//        List<Long> boardIds = new ArrayList<>();
//        professor.getBoardFunctionsList().stream().map(boardFunction ->
//                boardIds.add(boardFunction.getBoardFunctionId().getBoardId()));

//        studentRepository
