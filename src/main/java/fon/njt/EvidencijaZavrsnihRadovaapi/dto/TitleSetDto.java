package fon.njt.EvidencijaZavrsnihRadovaapi.dto;

import lombok.Data;

import java.util.Date;

@Data
public class TitleSetDto {
    private Long notificationId;

    private String topic;

    private String message;

    private Date createdAt;

    private boolean read;

    private UserProfileDto sender;

    private UserProfileDto user;

    String title;
}
