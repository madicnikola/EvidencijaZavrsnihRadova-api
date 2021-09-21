package fon.njt.EvidencijaZavrsnihRadovaapi.mapper;


import fon.njt.EvidencijaZavrsnihRadovaapi.dto.ThesisDto;
import fon.njt.EvidencijaZavrsnihRadovaapi.entity.GraduateThesis;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ThesisMapper {


    GraduateThesis map(ThesisDto thesisDto);

}
