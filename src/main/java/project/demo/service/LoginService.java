package project.demo.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import project.demo.domain.Member;
import project.demo.dto.LoginForm;
import project.demo.dto.MemberForm;
import project.demo.repository.MemberRepository;

import javax.swing.text.html.Option;
import java.util.Optional;

@RequiredArgsConstructor
public class LoginService {

    private final MemberRepository memberRepository;

    public Member login(String loginId, String password) {
        return memberRepository.findByLoginId(loginId)
                .filter(m -> m.getPassword().equals(password))
                .orElse(null);
    }

    public void register(MemberForm memberForm) {

    }
}
