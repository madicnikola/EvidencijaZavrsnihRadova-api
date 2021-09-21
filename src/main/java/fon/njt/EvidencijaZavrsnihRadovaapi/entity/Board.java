package fon.njt.EvidencijaZavrsnihRadovaapi.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private Long boardId;
    private int numberOfMembers;
    private Date dateOfFormation;
    @OneToMany(mappedBy = "board")
    @Singular("addProfessor")
    private List<BoardFunction> professors;

    @OneToOne
    @JsonIgnore
    private GraduateThesis graduateThesis;

}
