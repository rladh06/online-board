package project.demo.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import project.SessionConst;
import project.demo.domain.Member;
import project.demo.dto.LoginForm;
import project.demo.service.LoginService;
import project.demo.validator.LoginFormValidator;

@Controller
@RequiredArgsConstructor
@Slf4j
public class LoginController {

    private final LoginFormValidator validator;
    private final LoginService loginService;

    @InitBinder
    public void init(WebDataBinder webDataBinder) {
        webDataBinder.addValidators(validator);
    }


    @GetMapping("/login")
    public String login(@ModelAttribute("loginForm") LoginForm loginForm) {
        return "login";
    }

    @PostMapping("/login")
    public String loginConfirm(@ModelAttribute("loginForm")LoginForm loginForm, BindingResult bindingResult,
                               @RequestParam(defaultValue = "/") String redirectURL,
                               RedirectAttributes redirectAttributes,
                               HttpServletRequest request) {
        validator.validate(loginForm, bindingResult);

        if (bindingResult.hasErrors()) {
            return "login";
        }

        Member findLoginMember = loginService.login(loginForm.getLoginId(), loginForm.getPassword());

        if ((findLoginMember == null) && !bindingResult.hasErrors()){
            bindingResult.reject("loginFail", "아이디 또는 비밀번호가 맞지 않습니다.");
            return "login";
        }

        //세션이 있으면 있는 세션 반환, 없으면 신규 세션 생성
        HttpSession session = request.getSession();
        //세션에 로그인 회원 정보 보관
        session.setAttribute(SessionConst.LOGIN_MEMBER, findLoginMember);

        log.info("login?: {}", findLoginMember);
        redirectAttributes.addFlashAttribute("message", "로그인에 성공했습니다!");
        return "redirect:" + redirectURL;
    }
}
