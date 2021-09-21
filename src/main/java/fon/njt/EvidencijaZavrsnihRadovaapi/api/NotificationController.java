package fon.njt.EvidencijaZavrsnihRadovaapi.api;

import fon.njt.EvidencijaZavrsnihRadovaapi.dto.NotificationDto;
import fon.njt.EvidencijaZavrsnihRadovaapi.entity.Notification;
import fon.njt.EvidencijaZavrsnihRadovaapi.entity.UserProfile;
import fon.njt.EvidencijaZavrsnihRadovaapi.exceptions.UpdateException;
import fon.njt.EvidencijaZavrsnihRadovaapi.mapper.NotificationMapper;
import fon.njt.EvidencijaZavrsnihRadovaapi.service.NotificationService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;

@RestController
@RequestMapping("/notification")
@AllArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;
    private final NotificationMapper notificationMapper;


    @RequestMapping(value = "/user", method = RequestMethod.GET)
    public ResponseEntity<Object> getNotificationsByUser() {
        UserProfile user = notificationService.getLoggedInUser();

        List<Notification> notifications = notificationService.findByUser(user);

        Map<String, Object> response = new HashMap<String, Object>();
        response.put("notifications", mapListToDto(notifications));
        response.put("user", user);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }


    @PatchMapping(value = "/user")
    public ResponseEntity<Object> updateUserNotification(@Valid @RequestBody NotificationDto reqUpdateNotification) {

        Map<String, Object> response = notificationService.updateUserNotification(reqUpdateNotification, reqUpdateNotification.getUser());
        if (response.get("notification") == null) {
            throw new UpdateException("Notification not updated");
        }
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }


    private List<NotificationDto> mapListToDto(List<Notification> notifications) {
        List<NotificationDto> notifDtos = new ArrayList<>();
        for (Notification notif : notifications) {
            notifDtos.add(notificationMapper.map(notif));
        }
        return notifDtos;
    }

}
