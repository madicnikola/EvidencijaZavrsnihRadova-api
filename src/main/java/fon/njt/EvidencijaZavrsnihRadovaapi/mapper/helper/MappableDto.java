package fon.njt.EvidencijaZavrsnihRadovaapi.mapper.helper;

import fon.njt.EvidencijaZavrsnihRadovaapi.entity.Person;
import fon.njt.EvidencijaZavrsnihRadovaapi.mapper.helper.EntityMapper;

public interface MappableDto {
    Person map(EntityMapper mapper);
}
