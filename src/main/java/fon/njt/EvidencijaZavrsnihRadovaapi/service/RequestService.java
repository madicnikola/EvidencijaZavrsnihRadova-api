package fon.njt.EvidencijaZavrsnihRadovaapi.service;

import fon.njt.EvidencijaZavrsnihRadovaapi.dto.NotificationDto;
import fon.njt.EvidencijaZavrsnihRadovaapi.entity.Notification;
import fon.njt.EvidencijaZavrsnihRadovaapi.entity.Professor;
import fon.njt.EvidencijaZavrsnihRadovaapi.entity.Student;
import fon.njt.EvidencijaZavrsnihRadovaapi.exceptions.BadRequestBodyException;
import fon.njt.EvidencijaZavrsnihRadovaapi.exceptions.NotificationException;
import fon.njt.EvidencijaZavrsnihRadovaapi.repository.NotificationRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;

@Service
@AllArgsConstructor
public class RequestService {
    private final AuthService authService;
    private final StudentService studentService;
    private final ProfessorService professorService;
    private final NotificationRepository notificationRepository;

    public void processRequestTitle(Long professorId) {
        Student s = studentService.findByUsername(authService.getCurrentUser().getUsername());
        System.out.println("Prof id -----------------> " + professorId);
        Professor p = professorService.findById(professorId);
        Notification n = new Notification();
        n.setUser(p.getUserProfile());
        n.setSender(authService.getCurrentUser());
        n.setTopic("Zahtev za temu");
        n.setCreatedAt(new Date());
        n.setMessage("Student " + s.getName() + " " + s.getSurname() + " " + s.getIndexNumber() + " želi da dobije temu za diplomski rad");
        if(notificationRepository.findByUserAndSenderAndTopic(n.getUser(), n.getSender(), n.getTopic()).isPresent()){
           throw new NotificationException("Obavestenje je vec poslato!");
        }
        n = notificationRepository.save(n);
    }

    public void setTitle(Map<String, Object> map) {
        try {
            NotificationDto notif = (NotificationDto) map.get("notification");
            String title = (String) map.get("title");




        }catch (ClassCastException e){
            throw new BadRequestBodyException("Error retrieving notification or/and title data");
        }




    }
}
