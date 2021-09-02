package fon.njt.EvidencijaZavrsnihRadovaapi.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;


@Data
@Entity
public abstract class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long personId;
    private String name;
    private String surname;
    private Date birthDate;
    @OneToOne
    @JoinColumn
    private UserProfile userProfile;

}
