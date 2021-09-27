package fon.njt.EvidencijaZavrsnihRadovaapi.api;

import fon.njt.EvidencijaZavrsnihRadovaapi.dto.MessageDto;
import fon.njt.EvidencijaZavrsnihRadovaapi.entity.Document;
import fon.njt.EvidencijaZavrsnihRadovaapi.service.AuthService;
import fon.njt.EvidencijaZavrsnihRadovaapi.service.DocumentService;
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

import java.util.List;
import java.util.stream.Collectors;

@Controller
@AllArgsConstructor
@RequestMapping("/doc")
@CrossOrigin(origins = "http://localhost:4200")
public class DocumentController {
    private final DocumentService documentService;
    private final AuthService authService;
    private final FileStorageService storageService;
    private final GraduateThesisService thesisService;


    @PostMapping("/upload")
    public ResponseEntity<MessageDto> uploadDocument(@RequestParam("file") MultipartFile file, @RequestParam("type") String type) {

        String message;
        try {
            documentService.save(file, type);
            message = "Uploaded the document successfully: " + file.getOriginalFilename();
            return ResponseEntity.status(HttpStatus.OK).body(new MessageDto(message));
        } catch (Exception e) {
            message = "Could not upload the document: " + file.getOriginalFilename() + "!";
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new MessageDto(message));
        }
    }

    @PostMapping("/{folderName}")
    public ResponseEntity<MessageDto> updateDocument(@PathVariable String folderName, @RequestParam("filename") String filename, @RequestParam("status") String status) {
        System.out.println("status ----> " + status);
        Document doc = documentService.updateVisibilityStatus(folderName, filename, status);
        return ResponseEntity.ok().body(MessageDto.builder().message("File updated successfully: " + filename + "!").build());
    }

    @GetMapping("/{folderName}")
    public ResponseEntity<List<Document>> getDocuments(@PathVariable String folderName) {
        List<Document> docs = documentService.getDocuments(folderName);
        docs = docs.stream().map(document -> {
                    String url = MvcUriComponentsBuilder
                            .fromMethodName(DocumentController.class, "getFile", folderName, document.getName()).build().toString();
                    document.setFileLocation(url);
                    return document;
                }
        ).collect(Collectors.toList());
        return ResponseEntity.status(HttpStatus.OK).body(docs);
    }


    @GetMapping("/files/{folderName}")
    public ResponseEntity<List<Document>> getListFiles(@PathVariable String folderName) {
        List<Document> docs = storageService.loadAll(folderName).map(path -> {
            String filename = path.getFileName().toString();
            String url = MvcUriComponentsBuilder
                    .fromMethodName(DocumentController.class, "getFile", folderName, path.getFileName().toString()).build().toString();
            System.out.println(url);
            Document doc = documentService.findByLocationAndName(folderName, filename);
            doc.setFileLocation(url);
            return doc;
        }).collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.OK).body(docs);
    }

    @GetMapping("/files/{folderName}/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> getFile(@PathVariable String folderName, @PathVariable String filename) {
        Resource file = storageService.load(folderName, filename);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }

    @DeleteMapping("/files/{folderName}/{filename:.+}")
    public ResponseEntity<MessageDto> deleteFile(@PathVariable String folderName, @PathVariable String filename) {
        boolean deleted = storageService.delete(folderName, filename);
        if (deleted) {
            int r = documentService.delete(folderName, filename);
            if (r == 0)
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(MessageDto.builder().message("System could not  delete the document: " + filename + "!").build());

            return ResponseEntity.ok().body(MessageDto.builder().message("File deleted successfully: " + filename + "!").build());
        } else
            return ResponseEntity.ok().body(MessageDto.builder().message("System could not  delete the file: " + filename + "!").build());

    }


}

