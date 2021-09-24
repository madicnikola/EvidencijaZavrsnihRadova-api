package fon.njt.EvidencijaZavrsnihRadovaapi.api;

import fon.njt.EvidencijaZavrsnihRadovaapi.dto.GraduateThesisDto;
import fon.njt.EvidencijaZavrsnihRadovaapi.dto.MessageDto;
import fon.njt.EvidencijaZavrsnihRadovaapi.entity.GraduateThesis;
import fon.njt.EvidencijaZavrsnihRadovaapi.mapper.GraduateThesisMapper;
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
    public ResponseEntity<GraduateThesis> getMyThesisByStudentId(@PathVariable String id) {
        return ResponseEntity.status(HttpStatus.OK).body(graduateThesisService.getThesis(Long.parseLong(id)));
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
    public ResponseEntity<MessageDto> setTitle(@RequestBody Map<String, Object> requestBody) {
        graduateThesisService.setTitle(requestBody);
        return ResponseEntity.status(HttpStatus.OK).body(MessageDto.builder().message("Naziv teme je uspešno postavljen!").build());
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


}
