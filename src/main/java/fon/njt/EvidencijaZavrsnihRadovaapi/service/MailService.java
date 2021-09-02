package fon.njt.EvidencijaZavrsnihRadovaapi.service;

import fon.njt.EvidencijaZavrsnihRadovaapi.entity.NotificationEmail;
import fon.njt.EvidencijaZavrsnihRadovaapi.exceptions.MailServiceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

@Service
@Slf4j
public class MailService {
    private final String username;
    private final String password;
    private final fon.njt.EvidencijaZavrsnihRadovaapi.service.MailContentBuilder mailContentBuilder;


    @Autowired
    public MailService(@Value("${spring.mail.username}") String username, @Value("${spring.mail.password}") String password, MailContentBuilder mailContentBuilder) {
        this.username = username;
        this.password = password;
        this.mailContentBuilder = mailContentBuilder;
    }

    @Async
    public void sendMail(NotificationEmail notificationEmail) {
        System.out.println(username);
        Properties prop = new Properties();
        prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.smtp.port", "587");
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.starttls.enable", "true"); //TLS
        Session session = Session.getInstance(prop,
                new Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });
        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("cvBuilderApp@gmail.com"));
            message.setRecipients(
                    Message.RecipientType.TO,
                    InternetAddress.parse(notificationEmail.getRecipient())
            );
            message.setSubject(notificationEmail.getSubject());
            message.setContent(mailContentBuilder.build(notificationEmail.getBody()), "text/html");
            Transport.send(message);
            System.out.println("Done");
            log.info("Activation email sent! Recipient:" + notificationEmail.getRecipient());

        } catch (MessagingException e) {
            e.printStackTrace();
            throw new MailServiceException("Exception occurred when sending email.");
        }

    }
}
