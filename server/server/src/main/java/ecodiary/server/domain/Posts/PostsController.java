package ecodiary.server.domain.Posts;

import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequiredArgsConstructor
public class PostsController {

    private final PostsService postsService;

    @GetMapping("/api/v1/posts/get/mission/{id}")
    public String findMission(@PathVariable Long id){
        return postsService.findMission(id);
    }

    @GetMapping("/api/v1/posts/get/question/{id}")
    public String findQuestion(@PathVariable Long id){
        return postsService.findQuestion(id);
    }

    @GetMapping("/api/v1/posts/get/info/{id}")
    public String findInfo(@PathVariable Long id){
        return postsService.findInfo(id);
    }

    @GetMapping("/api/v1/posts/get/img-url/{id}")
    public String findImgUrl(@PathVariable Long id){
        return postsService.findImgUrl(id);
    }

    @GetMapping("/api/v1/posts/get/date/{date}")
    public Boolean findDate(@PathVariable @DateTimeFormat(pattern="yyyyMMdd") LocalDate date){
        return date.equals(LocalDate.now()); }
}
