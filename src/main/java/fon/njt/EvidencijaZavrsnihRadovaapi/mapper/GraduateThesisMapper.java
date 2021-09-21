package fon.njt.EvidencijaZavrsnihRadovaapi.mapper;

import fon.njt.EvidencijaZavrsnihRadovaapi.dto.GraduateThesisDto;
import fon.njt.EvidencijaZavrsnihRadovaapi.entity.GraduateThesis;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")

public interface GraduateThesisMapper {

    GraduateThesis map(GraduateThesisDto thesisDto);
    GraduateThesisDto map(GraduateThesis t);

}
