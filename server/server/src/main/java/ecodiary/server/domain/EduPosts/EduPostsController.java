package ecodiary.server.domain.EduPosts;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequiredArgsConstructor
public class EduPostsController {

    private final EduPostsService eduPostsService;

    @ResponseBody
    @PostMapping("/api/v1/eduPosts")
    public Long saveEduPosts(@RequestBody EduPostsDto eduPostsDto){
        return eduPostsService.saveMission(eduPostsDto);
    }


}
