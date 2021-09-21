package fon.njt.EvidencijaZavrsnihRadovaapi.service;

import fon.njt.EvidencijaZavrsnihRadovaapi.entity.GraduateThesis;
import fon.njt.EvidencijaZavrsnihRadovaapi.entity.Student;
import fon.njt.EvidencijaZavrsnihRadovaapi.exceptions.NotPresentException;
import fon.njt.EvidencijaZavrsnihRadovaapi.repository.StudentRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class StudentService {
    private final StudentRepository studentRepository;
    private final AuthService authService;

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

}
