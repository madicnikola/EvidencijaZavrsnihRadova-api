package fon.njt.EvidencijaZavrsnihRadovaapi.mapper;

import fon.njt.EvidencijaZavrsnihRadovaapi.dto.StudentDto;
import fon.njt.EvidencijaZavrsnihRadovaapi.entity.Student;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface StudentMapper {
    Student map(StudentDto data);

    StudentDto map(Student data);
}
