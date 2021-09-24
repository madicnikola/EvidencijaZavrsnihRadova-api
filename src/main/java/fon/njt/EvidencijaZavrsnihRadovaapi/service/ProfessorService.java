package fon.njt.EvidencijaZavrsnihRadovaapi.service;

import fon.njt.EvidencijaZavrsnihRadovaapi.dto.ProfessorDto;
import fon.njt.EvidencijaZavrsnihRadovaapi.entity.BoardFunction;
import fon.njt.EvidencijaZavrsnihRadovaapi.entity.Professor;
import fon.njt.EvidencijaZavrsnihRadovaapi.exceptions.NotPresentException;
import fon.njt.EvidencijaZavrsnihRadovaapi.mapper.ProfessorMapper;
import fon.njt.EvidencijaZavrsnihRadovaapi.repository.BoardFunctionRepository;
import fon.njt.EvidencijaZavrsnihRadovaapi.repository.ProfessorRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ProfessorService {
    private final ProfessorRepository professorRepository;
    private final ProfessorMapper mapper;
    private final BoardFunctionRepository boardFunctionRepository;

    public List<Professor> getAll() {
        return professorRepository.findAll();
    }

    public Professor findById(Long professorId) {
        Optional<Professor> p = professorRepository.findById(professorId);
        if (!p.isPresent()) {
            throw new NotPresentException("Professor not found");
        }
        return p.get();
    }

    public Professor findByUsername(String professorUsername) {
        Optional<Professor> p = professorRepository.findByUserProfileUsername(professorUsername);
        if (!p.isPresent()) {
            throw new NotPresentException("Professor not found");
        }
        return p.get();
    }

    public ProfessorDto getById(Long id) {
        Optional<Professor> p = professorRepository.findById(id);
        if (!p.isPresent()) {
            throw new NotPresentException("Professor not found");
        }
        return mapper.map(p.get());
    }

    public List<ProfessorDto> getByBoardFunction(Long id) {
        List<Professor> professors = new LinkedList<>();
        List<BoardFunction> boardFunctions = boardFunctionRepository.findByBoardGraduateThesisGraduateThesisId(id).orElse(null);
        boardFunctions.forEach(boardFunction -> {
            List<Professor> prof = professorRepository.findByBoardFunctionsListNotContaining(boardFunction);
            prof.forEach(professors::add);
        });
        return professors.stream().map(mapper::map).collect(Collectors.toList());
    }
}
