package project.demo.repository;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.weaver.Member;
import org.springframework.transaction.annotation.Transactional;
import project.demo.domain.Post;
import project.demo.dto.PostForm;

import java.util.List;
import java.util.Optional;

@Slf4j
@Transactional
public class MariaDBPostRepository implements PostRepository{

    private final EntityManager em;

    public MariaDBPostRepository(EntityManager em) {
        this.em = em;
    }

    @Override
    public Post save(Post post) {
        em.persist(post);
        return post;
    }

    @Override
    public void update(Long id, PostForm postForm) {
        Post post = em.find(Post.class, id);
//        post.setTitle(postForm.getTitle());
        post.setContent(postForm.getContent());
    }

    @Override
    public Optional<Post> findById(Long id) {
        Post post = em.find(Post.class, id);
        return Optional.ofNullable(post);
    }

    @Override
    public List<Post> findAll() {
        String jpql = "select p from Post p";
        return em.createQuery(jpql, Post.class)
                .getResultList();
    }

    @Override
    public void deleteById(Long id) {
        Post post = em.find(Post.class, id);
        if (post != null) {
            em.remove(post);
        }
    }
}
