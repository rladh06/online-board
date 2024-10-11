package project.demo.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class PostForm {

    @NotBlank
    private String title;

    @NotBlank
    private String content;

    private MultipartFile file;
}
