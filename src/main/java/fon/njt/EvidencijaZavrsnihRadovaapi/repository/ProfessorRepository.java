package fon.njt.EvidencijaZavrsnihRadovaapi.repository;

import fon.njt.EvidencijaZavrsnihRadovaapi.entity.Professor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProfessorRepository extends JpaRepository<Professor, Long> {

    Optional<Professor> findByUserProfileUsername(String username);
}
