package project.demo.config;


import jakarta.persistence.EntityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import project.SessionManager;
import project.demo.repository.*;
import project.demo.service.*;
import project.demo.validator.LoginFormValidator;
import project.demo.validator.MemberFormValidator;

@Configuration
public class AppConfig {

    private final EntityManager em;

    public AppConfig(EntityManager em) {
        this.em = em;
    }

    @Bean
    public MemberRepository memberRepository() {
        return new MariaDBMemberRepository(em);
    }

    @Bean
    public MemberService memberService() {
        return new MemberServiceImpl(memberRepository());
    }

    @Bean
    public LoginService loginService() {
        return new LoginService(memberRepository());
    }

    @Bean
    public PostRepository postRepository() {
        return new MariaDBPostRepository(em);
    }

    @Bean
    public PostService postService(){
        return new PostService(postRepository(), fileStorageProperties());
    }

    @Bean
    public MemberFormValidator memberFormValidator() {
        return new MemberFormValidator();
    }

    @Bean
    public LoginFormValidator loginFormValidator() {
        return new LoginFormValidator();
    }

    @Bean
    public SessionManager sessionManager() {
        return new SessionManager();
    }

    @Bean
    public FileStorageProperties fileStorageProperties() {
        return new FileStorageProperties();
    }

    @Bean
    public ImageUploadService imageUploadService() {
        return new ImageUploadService();
    }


}
