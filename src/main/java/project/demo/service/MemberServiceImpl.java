package project.demo.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import project.demo.dto.MemberForm;
import project.demo.repository.MemberRepository;
import project.demo.domain.Member;


@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;
    @Override
    public Member findMember(Long id) {
        return null;
    }

    @Override
    public Member memberDtoToMember(MemberForm memberForm) {

        return new Member(memberForm.getLoginId(), memberForm.getName(), memberForm.getPassword());
    }
}
