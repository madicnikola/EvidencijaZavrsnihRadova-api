package fon.njt.EvidencijaZavrsnihRadovaapi.dto;

import fon.njt.EvidencijaZavrsnihRadovaapi.entity.*;
import fon.njt.EvidencijaZavrsnihRadovaapi.mapper.helper.EntityMapper;
import fon.njt.EvidencijaZavrsnihRadovaapi.mapper.helper.MappableDto;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.Hibernate;

import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.List;
import java.util.Objects;

@Data
public class ProfessorDto extends PersonDto {
    private AcademicRank academicRank;
    private Title title;

    private List<BoardFunction> boardFunctionsList;
    private String department;

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
