package fon.njt.EvidencijaZavrsnihRadovaapi.api;


import fon.njt.EvidencijaZavrsnihRadovaapi.dto.StudentDto;
import fon.njt.EvidencijaZavrsnihRadovaapi.entity.Professor;
import fon.njt.EvidencijaZavrsnihRadovaapi.entity.Student;
import fon.njt.EvidencijaZavrsnihRadovaapi.mapper.StudentMapper;
import fon.njt.EvidencijaZavrsnihRadovaapi.service.AuthService;
import fon.njt.EvidencijaZavrsnihRadovaapi.service.ProfessorService;
import fon.njt.EvidencijaZavrsnihRadovaapi.service.StudentService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/students")
@AllArgsConstructor
public class StudentController {
    private final StudentService studentService;
    private final StudentMapper mapper;


    @GetMapping("/{username}")
    public ResponseEntity<StudentDto> getStudent(@PathVariable String username) {
        Student student = studentService.findByUsername(username);
        return ResponseEntity.status(HttpStatus.OK).body(mapper.map(student));
    }

    @GetMapping("/")
    public ResponseEntity<List<StudentDto>> getMyStudents() {
        List<StudentDto> students = studentService.getMyStudents();
        return ResponseEntity.status(HttpStatus.OK).body(students);
    }


    @GetMapping("/board/{username}")
    public ResponseEntity<List<StudentDto>> getStudentsByBoardMemberUsername(@PathVariable("username") String boardMemberUsername) {
        List<StudentDto> students = studentService.getStudentsByBoardMember(boardMemberUsername);
        return ResponseEntity.status(HttpStatus.OK).body(students);
    }

    @GetMapping("/mentor/{username}")
    public ResponseEntity<List<StudentDto>> getStudentsByMentorUsername(@PathVariable("username") String mentorUsername) {
        List<StudentDto> students = studentService.getStudentsByMentorUsername(mentorUsername);
        return ResponseEntity.status(HttpStatus.OK).body(students);
    }


    @GetMapping("/all")
    public ResponseEntity<List<StudentDto>> getAllStudents() {
        List<StudentDto> students = studentService.getAllStudents();
        return ResponseEntity.status(HttpStatus.OK).body(students);
    }


}
