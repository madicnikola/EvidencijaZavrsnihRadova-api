package fon.njt.EvidencijaZavrsnihRadovaapi.api;

import fon.njt.EvidencijaZavrsnihRadovaapi.dto.GraduateThesisDto;
import fon.njt.EvidencijaZavrsnihRadovaapi.dto.MessageDto;
import fon.njt.EvidencijaZavrsnihRadovaapi.dto.ProfessorDto;
import fon.njt.EvidencijaZavrsnihRadovaapi.entity.BoardFunction;
import fon.njt.EvidencijaZavrsnihRadovaapi.entity.GraduateThesis;
import fon.njt.EvidencijaZavrsnihRadovaapi.entity.Professor;
import fon.njt.EvidencijaZavrsnihRadovaapi.mapper.GraduateThesisMapper;
import fon.njt.EvidencijaZavrsnihRadovaapi.mapper.ProfessorMapper;
import fon.njt.EvidencijaZavrsnihRadovaapi.service.GraduateThesisService;
import fon.njt.EvidencijaZavrsnihRadovaapi.service.RequestService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/graduate-thesis")
@AllArgsConstructor
public class GraduateThesisController {
    private final GraduateThesisService graduateThesisService;
    private final GraduateThesisMapper mapper;
    private final ProfessorMapper professorMapper;
    private final RequestService requestService;

    @GetMapping("")
    public ResponseEntity<List<GraduateThesis>> getAll() {
        return ResponseEntity.status(HttpStatus.OK).body(graduateThesisService.getAll());
    }

    @GetMapping("/published")
    public ResponseEntity<List<GraduateThesis>> getAllPublished() {
        return ResponseEntity.status(HttpStatus.OK).body(graduateThesisService.getAllPublished());
    }


    @GetMapping("/my-thesis")
    public ResponseEntity<GraduateThesis> getMyThesis() {
        return ResponseEntity.status(HttpStatus.OK).body(graduateThesisService.getMyThesis());
    }

    @GetMapping("/{id}")
    public ResponseEntity<GraduateThesisDto> getThesis(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(graduateThesisService.getThesis(id));
    }

    @GetMapping("/student/{id}")
    public ResponseEntity<GraduateThesis> getMyThesisByStudentId(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(graduateThesisService.getThesisByStudentId(id));
    }

    @GetMapping("/student/user-profile/{username}")
    public ResponseEntity<GraduateThesisDto> getMyThesisByStudentUsername(@PathVariable String username) {
        return ResponseEntity.status(HttpStatus.OK).body(mapper.map(graduateThesisService.getThesisByStudentUsername(username)));
    }

    @GetMapping("/filter/{year}")
    public ResponseEntity<List<GraduateThesisDto>> getThesesByYear(@PathVariable int year) {
        return ResponseEntity.status(HttpStatus.OK).body(graduateThesisService.getThesesByYear(year));
    }

    @PostMapping
    public ResponseEntity saveThesis(@RequestBody GraduateThesisDto thesisDto) {
        graduateThesisService.save(thesisDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/request-title")
    public ResponseEntity<MessageDto> requestTitle(@RequestBody Long professorId) {
        graduateThesisService.processRequestTitle(professorId);
        return ResponseEntity.status(HttpStatus.CREATED).body(MessageDto.builder().message("Poslat je zahtev za temu!").build());
    }

    @PostMapping("/set-title")
    public ResponseEntity<GraduateThesisDto> setTitle(@RequestBody Map<String, Object> requestBody) {
        GraduateThesis graduateThesis = graduateThesisService.setTitle(requestBody);
        return ResponseEntity.status(HttpStatus.OK).body(mapper.map(graduateThesis));
    }

    @PostMapping("/publish")
    public ResponseEntity<GraduateThesisDto> publish(@RequestBody GraduateThesisDto thesisDto) {
        GraduateThesis graduateThesis = graduateThesisService.publish(thesisDto);
        return ResponseEntity.status(HttpStatus.OK).body(mapper.map(graduateThesis));
    }

    @PostMapping("/unpublish")
    public ResponseEntity<GraduateThesisDto> unpublish(@RequestBody GraduateThesisDto thesisDto) {
        GraduateThesis graduateThesis = graduateThesisService.unpublish(thesisDto);
        return ResponseEntity.status(HttpStatus.OK).body(mapper.map(graduateThesis));

    }

    @PutMapping("/update/{id}")
    public ResponseEntity<GraduateThesisDto> update(@RequestBody GraduateThesisDto thesisDto, @PathVariable Long id) {
        GraduateThesis graduateThesis = graduateThesisService.updateThesis(thesisDto, id);
        if (graduateThesis != null)
            return ResponseEntity.status(HttpStatus.OK).body(mapper.map(graduateThesis));

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);

    }

    @PostMapping("/board/add")
    public ResponseEntity<ProfessorDto> addBoardMember(@RequestParam Long boardId, @RequestParam Long professorId  ) {
        Professor prof = graduateThesisService.addBoardMember(boardId, professorId);
        if (prof != null)
            return ResponseEntity.status(HttpStatus.OK).body(professorMapper.map(prof));

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }
}
