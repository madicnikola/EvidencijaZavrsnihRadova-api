package fon.njt.EvidencijaZavrsnihRadovaapi.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Data
@Entity
public class Professor extends Person{

    @ManyToOne
    @JoinColumn
    private AcademicRank academicRank;
    @ManyToOne
    @JoinColumn
    private Title title;

}
