package fon.njt.EvidencijaZavrsnihRadovaapi.entity;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.util.Objects;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
public class Professor extends Person{

    @ManyToOne
    @JoinColumn
    private AcademicRank academicRank;
    @ManyToOne
    @JoinColumn
    private Title title;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Professor professor = (Professor) o;
        return Objects.equals(getPersonId(), professor.getPersonId());
    }

    @Override
    public int hashCode() {
        return 0;
    }
}
