package project.demo.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import project.demo.config.FileStorageProperties;
import project.demo.domain.Post;
import project.demo.dto.PostForm;
import project.demo.service.PostService;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class MariaDBPostRepositoryTest {

    @Autowired
    PostRepository repository;

    @Test
    void testUpdatePost() {
        Post post = new Post("Title", "Content", "Author");
        repository.save(post);
        Long postId = post.getId();

        PostForm postForm = new PostForm();
        postForm.setTitle("Updated Title");
        postForm.setContent("Updated Content");

        repository.update(postId, postForm);

        Optional<Post> updatedPost = repository.findById(postId);
        assertTrue(updatedPost.isPresent());
        assertEquals("Updated Title", updatedPost.get().getTitle());
        assertEquals("Updated Content", updatedPost.get().getContent());

        List<Post> allPosts = repository.findAll();
        assertEquals(1, allPosts.size()); // 업데이트 후에도 저장소에는 하나의 포스트만 있어야 함
    }

    @Test
    void testdeletePost() {
        Post post = new Post("Title", "Content", "Author");
        Post savedPost = repository.save(post);
        Long id = savedPost.getId();

        repository.deleteById(id);
        Assertions.assertThatThrownBy(() -> repository.findById(id).get())
                .isInstanceOf(NoSuchElementException.class);
    }
}