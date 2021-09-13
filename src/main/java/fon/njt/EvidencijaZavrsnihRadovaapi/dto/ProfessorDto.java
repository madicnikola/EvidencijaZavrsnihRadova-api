package fon.njt.EvidencijaZavrsnihRadovaapi.dto;

import fon.njt.EvidencijaZavrsnihRadovaapi.entity.AcademicRank;
import fon.njt.EvidencijaZavrsnihRadovaapi.entity.Title;
import lombok.Data;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Data
public class ProfessorDto extends PersonDto {
    private AcademicRank academicRank;
    private Title title;
}
