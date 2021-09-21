package fon.njt.EvidencijaZavrsnihRadovaapi.mapper;

import fon.njt.EvidencijaZavrsnihRadovaapi.dto.NotificationDto;
import fon.njt.EvidencijaZavrsnihRadovaapi.entity.Notification;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface NotificationMapper {
    Notification map(NotificationDto notifDto);
    NotificationDto map(Notification notif);


}
