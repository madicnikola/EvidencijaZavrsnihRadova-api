package fon.njt.EvidencijaZavrsnihRadovaapi.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Data
public class Title {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long titleId;
    private String name;
}
