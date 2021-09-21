package fon.njt.EvidencijaZavrsnihRadovaapi.entity;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private Long notificationId;

    private String topic;

    private String message;

    private Date createdAt;

    private boolean isRead;
    @ManyToOne
    private UserProfile sender;

    @ManyToOne
    private UserProfile user;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Notification that = (Notification) o;
        return Objects.equals(notificationId, that.notificationId);
    }

    @Override
    public int hashCode() {
        return 0;
    }
}
