package ecodiary.server.domain.User;

import ecodiary.server.domain.Posts.PostsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class UserController {

    private final UserService userService;
    private final PostsService postsService;


    @GetMapping("/api/v1/user/{id}/mission-id")
    public UserDto getMissionId(@PathVariable Long id){
        Long missionId=userService.selectMissionId(id);
        return UserDto.builder().missionId(missionId).mission(postsService.findMission(id)).build();
    }

    @PostMapping("/api/v1/user")
    public Long save(){
        return userService.createUser();
    }

    @PutMapping("/api/v1/user/{id}/mission-id")
    public void updateMissionId(@PathVariable Long id){
         userService.updateMissionId(id);
    }

    @PutMapping("/api/v1/user/{id}/mission-check")
    public void updateMissionDate(@PathVariable Long id){
        userService.updateMissionDate(id,LocalDate.now());
    }

    @GetMapping("/api/v1/user/total-of-today")
    public int getTotalOfToday(){
        return userService.countMissionCheck();
    }

    @PostMapping("/api/v1/edu-posts/register-admin-to-user")
    public List<User> registerAdminToUser(@RequestBody UserRegisterRequestDto userRegisterRequestDto){

        List<User> users=userService.registerAdminToUser(userRegisterRequestDto);
        System.out.println(users);
        return users;
    }


}
