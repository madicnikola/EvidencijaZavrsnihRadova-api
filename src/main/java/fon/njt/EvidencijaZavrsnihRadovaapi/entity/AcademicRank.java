package fon.njt.EvidencijaZavrsnihRadovaapi.entity;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Objects;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
public class AcademicRank {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long academicRankId;
    private String name;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        AcademicRank that = (AcademicRank) o;
        return Objects.equals(academicRankId, that.academicRankId);
    }

    @Override
    public int hashCode() {
        return 0;
    }
}
