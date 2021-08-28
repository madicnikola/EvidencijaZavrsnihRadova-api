package fon.njt.EvidencijaZavrsnihRadovaapi.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Email;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserProfile {
    @Id
    private Long userProfileId;
    private String username;
    @Email
    private String email;
    private String password;
    @ManyToOne
    @JoinColumn(nullable=false)
    private Role role;
    private boolean enabled;

}
