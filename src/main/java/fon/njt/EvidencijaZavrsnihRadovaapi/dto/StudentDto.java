package fon.njt.EvidencijaZavrsnihRadovaapi.dto;

import fon.njt.EvidencijaZavrsnihRadovaapi.entity.Department;
import lombok.Data;

@Data
public class StudentDto extends PersonDto {
    private String indexNumber;
    private String degreeOfStudy;
    private Department department;
}
