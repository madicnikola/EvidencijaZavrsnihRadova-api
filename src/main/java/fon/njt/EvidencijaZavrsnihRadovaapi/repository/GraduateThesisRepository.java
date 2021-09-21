package fon.njt.EvidencijaZavrsnihRadovaapi.repository;

import fon.njt.EvidencijaZavrsnihRadovaapi.entity.GraduateThesis;
import fon.njt.EvidencijaZavrsnihRadovaapi.entity.VisibilityStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface GraduateThesisRepository extends JpaRepository<GraduateThesis, Long> {

    @Query("select g from GraduateThesis g where g.visibilityStatus = ?1")
    Optional<List<GraduateThesis>> findAllByVisibilityStatus(VisibilityStatus status);

    Optional<GraduateThesis> findByStudentUserProfileUsername(String username);
}
