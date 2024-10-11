package project.demo.repository;

import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import project.demo.domain.Member;

import javax.swing.*;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
class MariaDBMemberRepositoryTest {

    @Autowired
    EntityManager em;

    @Autowired
    MemberRepository repository;


    @Test
    public void save() {
        Member member = new Member();
        member.setName("spring");
        member.setPassword("spring");
        member.setLoginId("spring");
        repository.save(member);

        Member savedMember = repository.findById(member.getId()).get();

        assertThat(savedMember).isEqualTo(member);
    }

    @Test
    public void findByName() {
        Member member1 = new Member();
        member1.setName("hello");
        member1.setPassword("hello");
        member1.setLoginId("hello");
        repository.save(member1);

        Member member2 = new Member();
        member2.setName("hello2");
        member2.setPassword("hello2");
        member2.setLoginId("hello2");
        repository.save(member2);

        Member result = repository.findByName("hello").get();

        assertThat(member1).isEqualTo(result);
    }

    @Test
    public void findById() {
        Member member = new Member();
        member.setName("spring");
        member.setPassword("spring");
        member.setLoginId("spring");
        Member savedMember = repository.save(member);
        Long id = savedMember.getId();

        Member byId = repository.findById(id).get();

        assertThat(byId).isEqualTo(member);
    }

    @Test
    public void findAll() {
        Member member1 = new Member();
        member1.setName("hello");
        member1.setPassword("hello");
        member1.setLoginId("hello");
        repository.save(member1);

        Member member2 = new Member();
        member2.setName("hello2");
        member2.setPassword("hello2");
        member2.setLoginId("hello2");
        repository.save(member2);

        List<Member> all = repository.findAll();

        assertThat(all.size()).isEqualTo(2);
    }


}