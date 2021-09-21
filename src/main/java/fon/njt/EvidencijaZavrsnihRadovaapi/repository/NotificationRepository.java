package fon.njt.EvidencijaZavrsnihRadovaapi.repository;


import fon.njt.EvidencijaZavrsnihRadovaapi.entity.Notification;
import fon.njt.EvidencijaZavrsnihRadovaapi.entity.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {
    Optional<List<Notification>> findByUser(UserProfile user);

    Optional<List<Notification>> findByUserUserProfileId(Long userId);

    Optional<Notification> findByUserAndNotificationId(UserProfile user, Long notificationId);

    Optional<Notification> findByUserAndSenderAndTopic(UserProfile userProfile, UserProfile sender, String topic);
}
