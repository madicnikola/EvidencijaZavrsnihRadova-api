package fon.njt.EvidencijaZavrsnihRadovaapi.repository;

import fon.njt.EvidencijaZavrsnihRadovaapi.entity.GraduateThesis;
import fon.njt.EvidencijaZavrsnihRadovaapi.entity.VisibilityStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface GraduateThesisRepository extends JpaRepository<GraduateThesis, Long> {

    List<GraduateThesis> findAllByVisibilityStatus(VisibilityStatus status);

    @Override
    List<GraduateThesis> findAll();

    Optional<GraduateThesis> findByStudent_UserProfileUsername(String username);
}
