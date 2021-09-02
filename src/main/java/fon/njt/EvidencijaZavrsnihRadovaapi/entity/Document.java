package fon.njt.EvidencijaZavrsnihRadovaapi.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Document {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
