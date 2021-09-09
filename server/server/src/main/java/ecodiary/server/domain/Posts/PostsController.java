package ecodiary.server.domain.Posts;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class PostsController {

    private final PostsService postsService;

    @GetMapping("/api/v1/posts/{id}/id")
    public String findMission(@PathVariable Long id){
        return postsService.findMission(id);
    }

    @GetMapping("/api/v1/posts/{id}/question")
    public String findQuestion(@PathVariable Long id){
        return postsService.findQuestion(id);
    }

    @GetMapping("/api/v1/posts/{id}/info")
    public String findInfo(@PathVariable Long id){
        return postsService.findInfo(id);
    }
}
