package fon.njt.EvidencijaZavrsnihRadovaapi.entity;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.util.Objects;

@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
public class Student extends Person {
    private String indexNumber;
    private String degreeOfStudy;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn()
    @ToString.Exclude
    private Department department;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Student student = (Student) o;
        return Objects.equals(getPersonId(), student.getPersonId());
    }

    @Override
    public int hashCode() {
        return 0;
    }
}
