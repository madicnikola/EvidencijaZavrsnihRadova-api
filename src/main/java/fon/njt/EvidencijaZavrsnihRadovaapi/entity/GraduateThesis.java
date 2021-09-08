package fon.njt.EvidencijaZavrsnihRadovaapi.entity;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class GraduateThesis {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long graduateThesisId;
    private String title;
    private String faculty;
    private String description;
    private Date dateOfReception;
    private Date dateOfBoardFormation;
    private Date dateOfThesisDefence;
    private ProgressStatus progressStatus;
    private VisibilityStatus visibilityStatus;

    @OneToOne
    private Student student;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        GraduateThesis that = (GraduateThesis) o;
        return Objects.equals(graduateThesisId, that.graduateThesisId);
    }

    @Override
    public int hashCode() {
        return 0;
    }
}
