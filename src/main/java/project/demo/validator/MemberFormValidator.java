package project.demo.validator;

import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import project.demo.dto.MemberForm;

import java.util.Objects;

@Slf4j
public class MemberFormValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return MemberForm.class.isAssignableFrom(clazz);
        //member == subMember
    }

    @Override
    public void validate(Object target, Errors errors) {
        MemberForm memberForm = (MemberForm) target;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "loginId", "required", "로그인 아이디는 필수입니다.");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors,"name","required", "이름은 필수입니다.");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "required", "비밀번호는 필수입니다.");


        // NotEmpty 검사가 통과된 경우에만 Size 검사 수행
        if (!errors.hasFieldErrors("loginId") && (memberForm.getLoginId().length() < 3 || memberForm.getLoginId().length() > 12)) {
            errors.rejectValue("loginId", "size", "아이디는 3자 이상 12자 이하로 입력해주세요.");
        }

        if (!errors.hasFieldErrors("name") && memberForm.getName().length() < 3) {
            errors.rejectValue("name", "size", "이름은 3자 이상 입력해주세요.");
        }

        if (!errors.hasFieldErrors("password") && memberForm.getPassword().length() < 3) {
            errors.rejectValue("password", "size", "비밀번호는 3자 이상 입력해주세요.");
        }

        // 비밀번호 확인 검사
        if (!errors.hasFieldErrors("password") && !memberForm.getPassword().equals(memberForm.getConfirmPassword())) {
            errors.rejectValue("confirmPassword", "passwordsDoNotMatch", "비밀번호가 일치하지 않습니다.");
        }

    }
}
