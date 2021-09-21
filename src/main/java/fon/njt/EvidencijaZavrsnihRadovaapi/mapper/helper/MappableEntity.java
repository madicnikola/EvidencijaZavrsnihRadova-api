package fon.njt.EvidencijaZavrsnihRadovaapi.mapper.helper;

import fon.njt.EvidencijaZavrsnihRadovaapi.dto.PersonDto;
import fon.njt.EvidencijaZavrsnihRadovaapi.mapper.helper.EntityMapper;

public interface MappableEntity {
    public PersonDto map(EntityMapper mapper);
}
