package project.demo.repository;


import project.demo.domain.Post;
import project.demo.dto.PostForm;

import java.util.*;

public class MemoryPostRepository implements PostRepository{

    private static final Map<Long, Post> postStore = new HashMap<>();

    private static Long sequence = 0L;

    @Override
    public Post save(Post post) {
        if (post.getId() == null) {
            post.setId(++sequence);
        }

        postStore.put(post.getId(), post);

        return post;
    }

    @Override
    public void update(Long id, PostForm postForm) {
        Post post = findById(id).orElseThrow();
        postForm.setTitle(post.getTitle());
        postForm.setContent(post.getContent());
        //postForm.change
    }

    @Override
    public Optional<Post> findById(Long id) {
        return Optional.ofNullable(postStore.get(id));
    }

    @Override
    public List<Post> findAll() {
        return new ArrayList<>(postStore.values());
    }

    public void deleteById(Long id) {
        postStore.remove(id);
    }


}
