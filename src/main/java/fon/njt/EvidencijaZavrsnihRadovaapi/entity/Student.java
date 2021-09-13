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
    @ManyToOne()
    @JoinColumn()
    @ToString.Exclude
    private Department department;

}
