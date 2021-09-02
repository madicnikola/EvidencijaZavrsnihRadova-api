package fon.njt.EvidencijaZavrsnihRadovaapi.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class FinalThesis {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long finalThesisId;
    private String title;
    private String faculty;
    private String description;
    private Date dateOfReception;
    private Date dateOfBoardFormation;
    private Date dateOfThesisDefence;
    private ProgressStatus progressStatus;
    private VisibilityStatus visibilityStatus;

    @OneToOne
    private Student student;
}
