package fon.njt.EvidencijaZavrsnihRadovaapi.mapper.helper;

import fon.njt.EvidencijaZavrsnihRadovaapi.dto.ProfessorDto;
import fon.njt.EvidencijaZavrsnihRadovaapi.dto.StudentDto;
import fon.njt.EvidencijaZavrsnihRadovaapi.entity.Professor;
import fon.njt.EvidencijaZavrsnihRadovaapi.entity.Student;
import fon.njt.EvidencijaZavrsnihRadovaapi.mapper.ProfessorMapper;
import fon.njt.EvidencijaZavrsnihRadovaapi.mapper.StudentMapper;
import fon.njt.EvidencijaZavrsnihRadovaapi.mapper.helper.EntityMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class PersonMapper implements EntityMapper {
    private final ProfessorMapper professorMapper;
    private final StudentMapper studentMapper;

    @Override
    public ProfessorDto map(Professor entity) {
        return professorMapper.map(entity);
    }

    @Override
    public StudentDto map(Student entity) {
        return studentMapper.map(entity);
    }

    @Override
    public Professor map(ProfessorDto entity) {
        return professorMapper.map(entity);
    }

    @Override
    public Student map(StudentDto entity) {
        return studentMapper.map(entity);
    }
}
