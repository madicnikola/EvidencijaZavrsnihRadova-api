package fon.njt.EvidencijaZavrsnihRadovaapi.repository;

import fon.njt.EvidencijaZavrsnihRadovaapi.entity.GraduateThesis;
import fon.njt.EvidencijaZavrsnihRadovaapi.entity.VisibilityStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;
@Repository
public interface GraduateThesisRepository extends JpaRepository<GraduateThesis, Long> {

    Optional<List<GraduateThesis>> findAllByVisibilityStatus(VisibilityStatus status);

    Optional<GraduateThesis> findByStudentUserProfileUsername(String username);

    Optional<GraduateThesis> findByStudentPersonId(Long id);

    Optional<GraduateThesis> findByBoardBoardId(Long boardId);

    Optional<List<GraduateThesis>> findByDateOfReceptionBetween(Date start, Date end);
}
