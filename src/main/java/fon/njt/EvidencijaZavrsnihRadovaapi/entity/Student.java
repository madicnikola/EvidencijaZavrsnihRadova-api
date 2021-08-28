package fon.njt.EvidencijaZavrsnihRadovaapi.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Data
@Entity
public class Student extends Person {
    private String indexNumber;
    private String degreeOfStudy;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn()
    private Department department;

}
