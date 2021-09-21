package fon.njt.EvidencijaZavrsnihRadovaapi.dto;

import fon.njt.EvidencijaZavrsnihRadovaapi.entity.Role;
import lombok.Data;

@Data
public class UserProfileDto {
    private Long userProfileId;
    private String username;
    private String email;
    private String password;
    private Role role;
    private boolean enabled;

}
