package fon.njt.EvidencijaZavrsnihRadovaapi.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import fon.njt.EvidencijaZavrsnihRadovaapi.dto.PersonDto;
import fon.njt.EvidencijaZavrsnihRadovaapi.mapper.helper.EntityMapper;
import fon.njt.EvidencijaZavrsnihRadovaapi.mapper.helper.MappableEntity;
import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.List;
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

    @OneToMany(mappedBy = "professor", fetch = FetchType.LAZY)
    private List<BoardFunction> boardFunctionsList;

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
