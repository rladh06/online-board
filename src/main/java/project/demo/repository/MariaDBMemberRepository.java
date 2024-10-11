package project.demo.repository;

import jakarta.persistence.Entity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import project.demo.domain.Member;

import java.util.List;
import java.util.Optional;

@Slf4j
@Transactional
public class MariaDBMemberRepository implements MemberRepository{

    private final EntityManager em;

    public MariaDBMemberRepository(EntityManager em) {
        this.em = em;
    }

    @Override
    public Member save(Member member) {
        em.persist(member);
        return member;
    }

    @Override
    public Optional<Member> findById(Long id) {
        Member member = em.find(Member.class, id);
        return Optional.ofNullable(member);
    }

    @Override
    public Optional<Member> findByName(String name) {
        String jpql = "select m from Member m where m.name = :name";
        Member findMember = em.createQuery(jpql, Member.class)
                .setParameter("name", name)
                .getSingleResult();
        return Optional.ofNullable(findMember);
    }

    @Override
    public Optional<Member> findByLoginId(String loginId) {
        String jpql = "select m from Member m where m.loginId = :loginId";
        Member findMember = em.createQuery(jpql, Member.class)
                .setParameter("loginId", loginId)
                .getSingleResult();
        return Optional.ofNullable(findMember);
    }

    @Override
    public List<Member> findAll() {
        String jpql = "select m from Member m";
        return em.createQuery(jpql, Member.class)
                .getResultList();
    }
}
