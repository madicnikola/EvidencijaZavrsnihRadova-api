package fon.njt.EvidencijaZavrsnihRadovaapi.dto;

import fon.njt.EvidencijaZavrsnihRadovaapi.entity.UserProfile;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotBlank;
import java.util.Date;

public abstract class PersonDto {
    private Long personId;
    private String name;
    private String surname;
    private Date birthDate;
    private UserProfile userProfile;
}
