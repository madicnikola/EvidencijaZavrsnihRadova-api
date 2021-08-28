package fon.njt.EvidencijaZavrsnihRadovaapi.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Document {
    @Id
    private Long documentId;
    private String name;
    private String description;
    private VisibilityStatus status;
    private String fileLocation;
    private String fileType;
    @ManyToOne
    @JoinColumn
    private DocumentType documentType;
    @ManyToOne
    @JoinColumn
    private FinalThesis finalThesis;
}
