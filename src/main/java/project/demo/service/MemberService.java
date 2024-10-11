package project.demo.service;

import project.demo.domain.Member;
import project.demo.dto.MemberForm;

public interface MemberService {
    Member findMember(Long id);
    Member memberDtoToMember(MemberForm memberForm);
}
