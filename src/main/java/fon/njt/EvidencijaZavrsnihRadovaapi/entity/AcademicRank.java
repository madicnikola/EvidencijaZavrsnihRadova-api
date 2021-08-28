package fon.njt.EvidencijaZavrsnihRadovaapi.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@Entity
public class AcademicRank {
    @Id
    private Long academicRankId;
    private String name;
}
