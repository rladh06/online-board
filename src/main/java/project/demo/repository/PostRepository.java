package project.demo.repository;

import project.demo.domain.Post;
import project.demo.dto.PostForm;

import java.util.List;
import java.util.Optional;

public interface PostRepository {

    Post save(Post post);

    void update(Long id, PostForm postForm);
    Optional<Post> findById(Long id);
    List<Post> findAll();
    void deleteById(Long id);
}
