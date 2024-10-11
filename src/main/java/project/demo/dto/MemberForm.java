package project.demo.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;


@Data
public class MemberForm {

    private String loginId;

    private String name;

    private String password;

    private String confirmPassword;
}
