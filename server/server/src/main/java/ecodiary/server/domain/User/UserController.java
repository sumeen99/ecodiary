package ecodiary.server.domain.User;

import ecodiary.server.domain.Posts.PostsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class UserController {

    private final UserService userService;
    private final PostsService postsService;

    @GetMapping("/api/v1/user/{id}/missionId")
    public UserDto getMissionId(@PathVariable Long id){
        Long missionId=userService.selectMissionId(id);
        return UserDto.builder().missionId(missionId).mission(postsService.findMission(missionId)).build();
    }

    @PostMapping("/api/v1/user")
    public Long save(){
        return userService.createUser();
    }

    @PutMapping("/api/v1/user/{id}/missionId")
    public void updateMissionId(@PathVariable Long id){
         userService.updateMissionId(id);
    }


}
