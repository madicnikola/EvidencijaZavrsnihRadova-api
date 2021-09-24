package fon.njt.EvidencijaZavrsnihRadovaapi.mapper;

import fon.njt.EvidencijaZavrsnihRadovaapi.dto.GraduateThesisDto;
import fon.njt.EvidencijaZavrsnihRadovaapi.entity.GraduateThesis;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")

public interface GraduateThesisMapper {

    GraduateThesis map(GraduateThesisDto thesisDto);
    GraduateThesisDto map(GraduateThesis t);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    GraduateThesis updateThesisFromDto(GraduateThesisDto dto, @MappingTarget GraduateThesis entity);

}
