package fon.njt.EvidencijaZavrsnihRadovaapi.api;

import fon.njt.EvidencijaZavrsnihRadovaapi.dto.ProfessorDto;
import fon.njt.EvidencijaZavrsnihRadovaapi.entity.Professor;
import fon.njt.EvidencijaZavrsnihRadovaapi.mapper.ProfessorMapper;
import fon.njt.EvidencijaZavrsnihRadovaapi.service.ProfessorService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/professor")
@AllArgsConstructor
public class ProfessorController {
    private final ProfessorService professorService;
    private final ProfessorMapper mapper;

    @GetMapping("/all")
    public ResponseEntity<List<Professor>> getAll() {
        return ResponseEntity.status(HttpStatus.OK).body(professorService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProfessorDto> getById(@PathVariable("id") Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(professorService.getById(id));
    }

    @GetMapping("/board/thesis/{id}")
    public ResponseEntity<List<ProfessorDto>> getByBoardFunction(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(professorService.getByBoardFunction(id));
    }

    @PostMapping("/")
    public ResponseEntity<List<ProfessorDto>> getAllByIds(@RequestBody List<Long> ids) {
        return ResponseEntity.status(HttpStatus.OK).body(professorService.getAllByIds(ids).stream().map(mapper::map).collect(Collectors.toList()));
    }



}
