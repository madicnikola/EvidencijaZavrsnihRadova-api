package fon.njt.EvidencijaZavrsnihRadovaapi.entity;


import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@Entity
public class Department {
    @Id
    private Long departmentId;
    private String name;
}
