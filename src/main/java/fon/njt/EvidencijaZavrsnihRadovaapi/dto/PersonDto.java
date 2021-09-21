package fon.njt.EvidencijaZavrsnihRadovaapi.dto;

import fon.njt.EvidencijaZavrsnihRadovaapi.entity.UserProfile;
import lombok.Data;

import java.util.Date;

@Data
public abstract class PersonDto {
    private Long personId;
    private String name;
    private String surname;
    private Date birthDate;
    private UserProfile userProfile;
}
