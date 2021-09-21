package fon.njt.EvidencijaZavrsnihRadovaapi.mapper.helper;

import fon.njt.EvidencijaZavrsnihRadovaapi.dto.PersonDto;
import fon.njt.EvidencijaZavrsnihRadovaapi.dto.ProfessorDto;
import fon.njt.EvidencijaZavrsnihRadovaapi.dto.StudentDto;
import fon.njt.EvidencijaZavrsnihRadovaapi.entity.Person;
import fon.njt.EvidencijaZavrsnihRadovaapi.entity.Professor;
import fon.njt.EvidencijaZavrsnihRadovaapi.entity.Student;

public interface EntityMapper {
    ProfessorDto map(Professor entity);
    StudentDto map(Student entity);

    Professor map(ProfessorDto entity);
    Student map(StudentDto entity);
}
