package fon.njt.EvidencijaZavrsnihRadovaapi.service;

import fon.njt.EvidencijaZavrsnihRadovaapi.dto.NotificationDto;
import fon.njt.EvidencijaZavrsnihRadovaapi.dto.UserProfileDto;
import fon.njt.EvidencijaZavrsnihRadovaapi.entity.Notification;
import fon.njt.EvidencijaZavrsnihRadovaapi.entity.UserProfile;
import fon.njt.EvidencijaZavrsnihRadovaapi.exceptions.NotPresentException;
import fon.njt.EvidencijaZavrsnihRadovaapi.mapper.NotificationMapper;
import fon.njt.EvidencijaZavrsnihRadovaapi.repository.NotificationRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@AllArgsConstructor
public class NotificationService {

    private final NotificationRepository notificationRepository;
    private final AuthService authService;
    private final NotificationMapper mapper;

    public UserProfile getLoggedInUser() {
        return authService.getCurrentUser();
    }

    public List<Notification> findByUser(UserProfile user) {
        Optional<List<Notification>> notifs = notificationRepository.findByUser(user);
        if (!notifs.isPresent()) {
            throw new NotPresentException("Notifications not found");
        }
        return notifs.get();
    }

    public Map<String, Object> updateUserNotification(NotificationDto notificationDto, UserProfileDto user) {
        Notification notif = notificationRepository.save(mapper.map(notificationDto));

        return new HashMap<String, Object>() {{
            put("notification", mapper.map(notif));
            put("message", "Notification updated");
        }};
    }

//    public Map<String,Object> updateUserNotification(Notitfication notitfication,User user){
//
//        notification = save(notification);
//        if(notification == null){
//            return KYCUtilities._errorMultipleObject(MessageUtility.getErrorMessage("NotificationNotUpdated"), HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//
//        return KYCUtilities._successMultipleObject(ObjectMap.objectMap(notification),MessageUtility.getSuccessMessage("NotificationUpdated"));
//    }

//    public Notification save(Notification notification){
//        try{
//            return notificationRepository.save(notification);
//        }catch (Exception e) {
//            logger.error("Exception occur while save Notification ",e);
//            return null;
//        }
//    }
//
//
//    public Notification findByUser(User user){
//        try{
//            return notificationRepository.findByUser(user);
//        }catch (Exception e) {
//            logger.error("Exception occur while fetch Notification by User ",e);
//            return null;
//        }
//    }
//
//    public List<Notification> findByUser(User user,Integer limit){
//        try{
//            return notificationRepository.userNotification(user.getUid(), new PageRequest(0, limit));
//        }catch (Exception e) {
//            logger.error("Exception occur while fetch Notification by User ",e);
//            return null;
//        }
//    }
//
//    public Notification createNotificationObject(String message,User user){
//        return new Notification(message,new Date(),user);
//    }
//
//    public Notification findByUserAndNotificationId(User user,Integer notificationId){
//        try{
//            return notificationRepository.findByUserAndNotificationId(user,notificationId);
//        }catch (Exception e) {
//            logger.error("Exception occur while fetch Notification by User and Notification Id ",e);
//            return null;
//        }
//    }

}
