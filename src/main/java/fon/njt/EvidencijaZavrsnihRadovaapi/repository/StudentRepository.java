package fon.njt.EvidencijaZavrsnihRadovaapi.repository;

import fon.njt.EvidencijaZavrsnihRadovaapi.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, Long> {

    Optional<Student> findByUserProfileUsername(String username);

    Optional<List<Student>> findByMentorPersonId(Long id);
    Optional<List<Student>> findByMentorUserProfileUsername(String  username);



}
