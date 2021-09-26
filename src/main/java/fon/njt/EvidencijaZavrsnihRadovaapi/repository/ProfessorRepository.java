package fon.njt.EvidencijaZavrsnihRadovaapi.repository;

import fon.njt.EvidencijaZavrsnihRadovaapi.entity.BoardFunction;
import fon.njt.EvidencijaZavrsnihRadovaapi.entity.Professor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProfessorRepository extends JpaRepository<Professor, Long> {

    Optional<Professor> findByUserProfileUsername(String username);


    Optional<List<Professor>> findAllByPersonIdNotIn(Iterable<Long> professorId);

    List<Professor> findAllByPersonIdIn(List<Long> ids);

    List<Professor> findByBoardFunctionsListNotContaining(BoardFunction boardFunction);
}
