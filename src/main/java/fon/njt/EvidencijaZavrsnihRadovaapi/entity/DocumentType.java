package fon.njt.EvidencijaZavrsnihRadovaapi.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
public class DocumentType {
    @Id
    private Long documentTypeId;
    private String name;
}
