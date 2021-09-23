package fon.njt.EvidencijaZavrsnihRadovaapi.entity;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Objects;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
public class DocumentType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long documentTypeId;
    private String name;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        DocumentType that = (DocumentType) o;
        return Objects.equals(documentTypeId, that.documentTypeId);
    }

    @Override
    public int hashCode() {
        return 0;
    }
}
