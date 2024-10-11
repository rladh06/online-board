package project.demo.service;

import lombok.RequiredArgsConstructor;
import org.springframework.web.multipart.MultipartFile;
import project.demo.exception.BadRequestException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

public class ImageUploadService {

    public String saveFile(MultipartFile file) {
        try {
            String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
            String uploadDir = "D:/";
            Path filePath = Paths.get(uploadDir, fileName);
            Files.copy(file.getInputStream(), filePath);
            return "/images/" + fileName;
        } catch (IOException e) {
            throw new BadRequestException("파일을 저장하지 못하였습니다.", e);
        }
    }
}
