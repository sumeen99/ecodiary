package ecodiary.server.domain.EduPosts;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class EduPostsController {

    private final EduPostsService eduPostsService;

    @ResponseBody
    @PostMapping("/api/v1/edu-posts/save")
    public Long saveEduPosts(@RequestBody EduPostsDto eduPostsDto) {
        return eduPostsService.saveMission(eduPostsDto);
    }

    @ResponseBody
    @PutMapping("/api/v1/edu-posts/update/{id}")
    public Long updateEduPosts(@RequestBody EduPostsResponseDto eduPostsResponseDto, @PathVariable Long id) {
        return eduPostsService.updateEduPosts(eduPostsResponseDto, id);
    }

    @ResponseBody
    @DeleteMapping("/api/v1/edu-posts/delete/{id}")
    public Long deleteEduPosts(@PathVariable Long id) {
        eduPostsService.deleteEduPosts(id);
        return id;
    }

}
