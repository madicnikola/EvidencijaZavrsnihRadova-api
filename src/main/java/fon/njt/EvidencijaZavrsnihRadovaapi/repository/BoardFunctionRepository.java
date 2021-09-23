package fon.njt.EvidencijaZavrsnihRadovaapi.repository;

import fon.njt.EvidencijaZavrsnihRadovaapi.entity.BoardFunction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BoardFunctionRepository extends JpaRepository<BoardFunction, Long> {

    Optional<List<BoardFunction>> findAllByFunctionAndProfessorPersonId(String function, Long id);
}
