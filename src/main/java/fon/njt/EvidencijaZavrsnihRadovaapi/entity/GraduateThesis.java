package fon.njt.EvidencijaZavrsnihRadovaapi.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import javafx.scene.input.KeyCode;
import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

import static javax.persistence.FetchType.LAZY;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
@AllArgsConstructor
@Builder
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
    private Date dateOfThesisSubmission;
    private ProgressStatus progressStatus;
    private VisibilityStatus visibilityStatus;
    private int grade;

    @OneToOne
    private Board board;

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
