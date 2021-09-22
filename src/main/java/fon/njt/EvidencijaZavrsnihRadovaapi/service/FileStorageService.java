package fon.njt.EvidencijaZavrsnihRadovaapi.service;

import fon.njt.EvidencijaZavrsnihRadovaapi.entity.Student;
import lombok.AllArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

@Service
@AllArgsConstructor
public class FileStorageService {
    private final GraduateThesisService thesisService;
    private final StudentService studentService;
    private final Path root = Paths.get("uploads");

    public void init() {
        try {
            Files.createDirectory(root);
        } catch (IOException e) {
            throw new RuntimeException("Could not initialize folder for upload!");
        }
    }

    public void save(MultipartFile file) {
//        String thesisName = thesisService.getMyThesis().getTitle();
        String folderName = getFolderName();
        Path path = root.resolve(folderName);
        if (!Files.exists(path))
            createDirectory(path);
        try {
            Files.copy(file.getInputStream(), path.resolve(file.getOriginalFilename()));
        } catch (Exception e) {
            throw new RuntimeException("Could not store the file. Error: " + e.getMessage());
        }
    }


    public Resource load(String folderName, String filename) {
        try {
            Path folderPath = root.resolve(folderName);
            Path file = folderPath.resolve(filename);
            Resource resource = new UrlResource(file.toUri());

            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new RuntimeException("Could not read the file!");
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException("Error: " + e.getMessage());
        }
    }

    public void deleteAll() {
        FileSystemUtils.deleteRecursively(root.toFile());
    }

    public Stream<Path> loadAll(String folderName) {
        try {
            Path rootPath = root.resolve(folderName);
            return Files.walk(rootPath, 1).filter(path -> !path.equals(rootPath)).map(rootPath::relativize);
        } catch (IOException e) {
            throw new RuntimeException("Could not load the files!");
        }
    }

    public boolean delete(String folderName, String filename) {
       return FileSystemUtils.deleteRecursively(root.resolve(folderName).resolve(filename).toFile());
    }


    public Path createDirectory(Path path) {
        try {
            Files.createDirectory(path);
        } catch (IOException e) {
            throw new RuntimeException("Could not initialize folder for upload!");
        }
        return path;
    }

    private String getFolderName() {
        String folderName = studentService.getCurrent().getIndexNumber();
        String[] indexNum = folderName.split("/");
        folderName = indexNum[0] + indexNum[1];
        System.out.println(folderName);
        return folderName;
    }
}
