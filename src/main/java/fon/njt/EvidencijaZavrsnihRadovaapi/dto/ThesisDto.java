package fon.njt.EvidencijaZavrsnihRadovaapi.dto;

import fon.njt.EvidencijaZavrsnihRadovaapi.entity.ProgressStatus;
import fon.njt.EvidencijaZavrsnihRadovaapi.entity.Student;
import fon.njt.EvidencijaZavrsnihRadovaapi.entity.VisibilityStatus;
import lombok.Data;

import java.util.Date;

@Data
public class ThesisDto {
    private Long graduateThesisId;
    private String title;
    private String faculty;
    private String description;
    private Date dateOfReception;
    private Date dateOfBoardFormation;
    private Date dateOfThesisDefence;
    private ProgressStatus progressStatus;
    private VisibilityStatus visibilityStatus;

    private Student student;

}
