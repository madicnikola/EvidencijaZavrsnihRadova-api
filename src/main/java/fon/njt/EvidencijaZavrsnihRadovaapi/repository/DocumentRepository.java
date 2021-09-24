package fon.njt.EvidencijaZavrsnihRadovaapi.repository;

import fon.njt.EvidencijaZavrsnihRadovaapi.entity.Document;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DocumentRepository extends JpaRepository<Document, Long> {
    int deleteByFileLocationAndName(String fileLocation, String name);

    Optional<Document> findByFileLocationAndName(String fileLocation, String name);

    Optional<List<Document>> findByFileLocation(String folderName);

    Optional<List<Document>> findByFinalThesisGraduateThesisId(Long id);

}
