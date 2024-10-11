package project.demo.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import project.SessionConst;
import project.demo.config.FileStorageProperties;
import project.demo.domain.Member;
import project.demo.domain.Post;
import project.demo.dto.PostForm;
import project.demo.repository.PostRepository;

@RequiredArgsConstructor
public class PostService {

    private final PostRepository repository;
    private final FileStorageProperties fileStorageProperties;

    public Post postFormToPost(PostForm postForm, Member loginMember) {
        if (loginMember == null) {

            throw new IllegalStateException("로그인한 사용자를 찾을 수 없습니다.");
        }

        Post post = new Post(postForm.getTitle(), loginMember.getName(), postForm.getContent());
        post.setFilepath(fileStorageProperties.getDir());

        return post;
    }

}
