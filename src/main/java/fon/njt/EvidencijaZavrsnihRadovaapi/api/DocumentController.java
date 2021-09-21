package fon.njt.EvidencijaZavrsnihRadovaapi.api;

import java.util.List;
import java.util.stream.Collectors;

import fon.njt.EvidencijaZavrsnihRadovaapi.dto.MessageDto;
import fon.njt.EvidencijaZavrsnihRadovaapi.entity.Document;
import fon.njt.EvidencijaZavrsnihRadovaapi.service.FileStorageService;
import fon.njt.EvidencijaZavrsnihRadovaapi.service.GraduateThesisService;
import lombok.AllArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

@Controller
@AllArgsConstructor
@RequestMapping("/doc")
public class DocumentController {
    private final FileStorageService storageService;
    private final GraduateThesisService thesisService;

    @PostMapping("/upload")
    public ResponseEntity<MessageDto> uploadFile(@RequestParam("file") MultipartFile file) {

        String message;
        try {
            storageService.save(file);
            message = "Uploaded the file successfully: " + file.getOriginalFilename();
            return ResponseEntity.status(HttpStatus.OK).body(new MessageDto(message));
        } catch (Exception e) {
            message = "Could not upload the file: " + file.getOriginalFilename() + "!";
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new MessageDto(message));
        }
    }

    @GetMapping("/files/{folderName}")
    public ResponseEntity<List<Document>> getListFiles(@PathVariable String folderName) {
        List<Document> fileInfos = storageService.loadAll(folderName).map(path -> {
            String filename = path.getFileName().toString();
            String url = MvcUriComponentsBuilder
                    .fromMethodName(DocumentController.class, "getFile", folderName, path.getFileName().toString()).build().toString();
            System.out.println(url);
            return Document.builder().fileLocation(url).name(filename).build();
        }).collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.OK).body(fileInfos);
    }

    @GetMapping("/files/{folderName}/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> getFile(@PathVariable String folderName, @PathVariable String filename) {
        Resource file = storageService.load(folderName, filename);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }

}

