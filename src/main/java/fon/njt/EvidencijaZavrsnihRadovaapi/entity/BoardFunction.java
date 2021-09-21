package fon.njt.EvidencijaZavrsnihRadovaapi.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import fon.njt.EvidencijaZavrsnihRadovaapi.entity.key.BoardFunctionKey;
import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BoardFunction {
    @EmbeddedId
    private BoardFunctionKey boardFunctionId;

    @ManyToOne
    @MapsId("professorId")
    @JoinColumn
    @JsonIgnore
    private Professor professor;

    @ManyToOne
    @MapsId("boardId")
    @JoinColumn
    @JsonIgnore
    private Board board;

    private String function;

    private Date joinDate;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        BoardFunction that = (BoardFunction) o;
        return Objects.equals(boardFunctionId, that.boardFunctionId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(boardFunctionId);
    }
}
