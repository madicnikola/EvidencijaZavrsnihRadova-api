package fon.njt.EvidencijaZavrsnihRadovaapi.api;

import fon.njt.EvidencijaZavrsnihRadovaapi.dto.ThesisDto;
import fon.njt.EvidencijaZavrsnihRadovaapi.entity.GraduateThesis;
import fon.njt.EvidencijaZavrsnihRadovaapi.service.GraduateThesisService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("graduate-thesis/")
@AllArgsConstructor
public class GraduateThesisController {
    private final GraduateThesisService graduateThesisService;

    @GetMapping("")
    public ResponseEntity<List<GraduateThesis>> getAll() {
        return ResponseEntity.status(HttpStatus.OK).body(graduateThesisService.getAll());
    }

    @GetMapping("published")
    public ResponseEntity<List<GraduateThesis>> getAllPublished() {
        return ResponseEntity.status(HttpStatus.OK).body(graduateThesisService.getAllPublished());
    }

    @PostMapping
    public ResponseEntity saveThesis(@RequestBody ThesisDto thesisDto){
        graduateThesisService.save(thesisDto);
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @GetMapping("my-thesis")
    public ResponseEntity<GraduateThesis> getThesis(@RequestHeader HttpHeaders headers){
        return ResponseEntity.status(HttpStatus.OK).body(graduateThesisService.get(headers)) ;
    }




}
