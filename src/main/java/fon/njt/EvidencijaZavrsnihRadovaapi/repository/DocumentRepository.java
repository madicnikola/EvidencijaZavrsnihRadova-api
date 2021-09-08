package fon.njt.EvidencijaZavrsnihRadovaapi.repository;

import fon.njt.EvidencijaZavrsnihRadovaapi.entity.Document;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DocumentRepository extends JpaRepository<Document, Long> {
}
