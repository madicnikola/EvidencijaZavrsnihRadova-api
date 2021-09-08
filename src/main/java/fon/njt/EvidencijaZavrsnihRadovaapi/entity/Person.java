package fon.njt.EvidencijaZavrsnihRadovaapi.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Date;


@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
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
