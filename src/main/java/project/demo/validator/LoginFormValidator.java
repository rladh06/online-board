package project.demo.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import project.demo.dto.LoginForm;

public class LoginFormValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return LoginForm.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        LoginForm loginForm = (LoginForm) target;

        // 순서 보장: NotEmpty 먼저 검사
        ValidationUtils.rejectIfEmptyOrWhitespace(errors,"loginId", "required", "로그인 아이디는 필수입니다.");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors,"password","required", "비밀번호는 필수입니다.");

        // 순서 보장: Size 검사
        if (!errors.hasFieldErrors("loginId") && (loginForm.getLoginId().length() < 3 || loginForm.getLoginId().length() > 12)) {
            errors.rejectValue("loginId", "size", "아이디는 3자 이상 12자 이하로 입력해주세요.");
        }

        if (!errors.hasFieldErrors("password") && loginForm.getPassword().length() < 3) {
            errors.rejectValue("password", "size", "비밀번호는 3자 이상 입력해주세요.");
        }
    }
}
