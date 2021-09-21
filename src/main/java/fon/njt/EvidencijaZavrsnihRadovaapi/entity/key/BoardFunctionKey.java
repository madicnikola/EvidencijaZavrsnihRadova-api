package fon.njt.EvidencijaZavrsnihRadovaapi.entity.key;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class BoardFunctionKey implements Serializable {
    @Column
    Long professorId;
    @Column
    Long boardId;






    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        BoardFunctionKey that = (BoardFunctionKey) o;
        return Objects.equals(professorId, that.professorId)
                && Objects.equals(boardId, that.boardId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(professorId, boardId);
    }
}
