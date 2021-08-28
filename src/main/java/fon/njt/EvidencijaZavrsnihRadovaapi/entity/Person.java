package fon.njt.EvidencijaZavrsnihRadovaapi.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import java.util.Date;


@Data
@Entity
public abstract class Person {
    @Id
    private Long personId;
    private String name;
    private String surname;
    private Date birthDate;
    @OneToOne
    @JoinColumn
    private UserProfile userProfile;

}
