package fon.njt.EvidencijaZavrsnihRadovaapi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
    @NotNull
    private String name;
    @NotNull
    private String surname;
    @NotNull
    private Date birthDate;
    @NotNull
    private String username;
    @NotNull
    private String email;
    @NotNull
    private String password;
    private String role;
    private String index;
    private String degreeOfStudy;
    private String department;
    private String academicRank;
    private String title;

}
