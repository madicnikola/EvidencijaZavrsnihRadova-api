package fon.njt.EvidencijaZavrsnihRadovaapi.repository;

import fon.njt.EvidencijaZavrsnihRadovaapi.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Long> {

}
