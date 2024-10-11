package project.demo.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import project.demo.domain.Member;
import project.demo.dto.MemberForm;
import project.demo.repository.MemberRepository;
import project.demo.service.MemberService;
import project.demo.validator.MemberFormValidator;

@Controller
@RequestMapping("/members")
@RequiredArgsConstructor
@Slf4j
public class RegisterController {

    private final MemberRepository memberRepository;
    private final MemberService memberService;
    private final MemberFormValidator memberFormValidator;

    public void init(WebDataBinder webDataBinder) {
        webDataBinder.addValidators(memberFormValidator);
    }


    @GetMapping("/add")
    public String addMember(@ModelAttribute("memberForm") MemberForm memberForm) {
        return "addUserForm";
    }

    @PostMapping("/add")
    public String save(@ModelAttribute("memberForm") MemberForm memberForm, BindingResult bindingResult) {

        memberFormValidator.validate(memberForm, bindingResult);

        if (bindingResult.hasErrors()) {
            return "addUserForm";
        }

        Member member = memberService.memberDtoToMember(memberForm);
        memberRepository.save(member);


        return "redirect:/";


    }
}
