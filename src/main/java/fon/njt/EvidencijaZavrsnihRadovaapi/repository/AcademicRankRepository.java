package fon.njt.EvidencijaZavrsnihRadovaapi.repository;

import fon.njt.EvidencijaZavrsnihRadovaapi.entity.AcademicRank;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AcademicRankRepository extends JpaRepository<AcademicRank, Long> {

    AcademicRank getByName(String name);
}
