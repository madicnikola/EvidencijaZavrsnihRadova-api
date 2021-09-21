package fon.njt.EvidencijaZavrsnihRadovaapi.repository;

import fon.njt.EvidencijaZavrsnihRadovaapi.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<Board,Long> {
}
