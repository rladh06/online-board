package project.demo.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.Part;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StreamUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import project.SessionConst;
import project.demo.config.FileStorageProperties;
import project.demo.domain.Member;
import project.demo.domain.Post;
import project.demo.dto.PostForm;
import project.demo.repository.PostRepository;
import project.demo.service.PostService;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@RequestMapping("/posts")
@Slf4j
public class PostsController {

    private final PostRepository postRepository;
    private final PostService postService;
    private final FileStorageProperties fileStorageProperties;

    @GetMapping
    public String listPosts(Model model) {
        model.addAttribute("posts", postRepository.findAll());
        return "posts";
    }

    @GetMapping("/new")
    public String newPostForm(@ModelAttribute("postForm") PostForm postForm) {
        return "newPost";
    }

    @GetMapping("edit/{id}")
    public String editForm(@PathVariable Long id, @ModelAttribute("postForm") PostForm postForm) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid Post Id : " + id));
        postForm.setTitle(post.getTitle());
        postForm.setContent(post.getContent());
        return "editPost";
    }

    @PostMapping("/new")
    public String createPost(@Validated @ModelAttribute PostForm postForm, BindingResult bindingResult,
                             @SessionAttribute(name = SessionConst.LOGIN_MEMBER) Member loginMember,
                             HttpServletRequest request) throws ServletException, IOException {

        log.info("bindingResult : {}", bindingResult);

        if (bindingResult.hasErrors()) {
            return "newPost";
        }

//        Collection<Part> parts = request.getParts();
//
//        for (Part part : parts) {
//            log.info("=== PART ===");
//            log.info("name={}", part.getName());
//            Collection<String> headerNames = part.getHeaderNames();
//            for (String headerName : headerNames) {
//                log.info("header {}: {}", headerName, part.getHeader(headerName));
//            }
//            //편의 메소드
//            //content-disposition; filename
//            log.info("submittedFilename={}", part.getSubmittedFileName());
//            log.info("size={}", part.getSize());
//
//            //파일에 저장하기
//            if(StringUtils.hasText(part.getSubmittedFileName())){
//                String fullPath = fileStorageProperties.getDir() + "/" + part.getSubmittedFileName();
//                log.info("파일 저장 fullPath={}", fullPath);
//                part.write(fullPath);
//            }


//        }

        Post post = postService.postFormToPost(postForm, loginMember);
        postRepository.save(post);
        return "redirect:/posts";
    }

    @PostMapping("edit/{id}")
    public String updateForm(@PathVariable Long id, @Validated @ModelAttribute PostForm postForm, BindingResult bindingResult) {

        log.info("bindingResult : {}", bindingResult);

        if (bindingResult.hasErrors()) {
            return "editPost";
        }


        postRepository.update(id, postForm);
        return "redirect:/posts";
    }

    @PostMapping("/delete/{id}")
    public String deleteForm(@PathVariable Long id) {
        postRepository.deleteById(id);
        return "redirect:/posts";
    }

    @GetMapping("/{id}")
    public String postDetailForm(@PathVariable Long id, Model model) {
        Post post = postRepository.findById(id).orElseThrow();
        log.info("post {}", post);
        model.addAttribute("post", post);
        return "postDetail";
    }
}
