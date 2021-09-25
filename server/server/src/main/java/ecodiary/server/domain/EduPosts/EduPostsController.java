package ecodiary.server.domain.EduPosts;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
@RequiredArgsConstructor
public class EduPostsController {

    private final EduPostsService eduPostsService;

    @PostMapping("/api/v1/eduPosts")
    public void saveEduPosts(@RequestBody EduPostsDto eduPostsDto){
        eduPostsService.saveMission(eduPostsDto);
    }


}
