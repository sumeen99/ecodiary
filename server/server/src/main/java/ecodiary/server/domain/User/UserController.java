package ecodiary.server.domain.User;

import ecodiary.server.domain.Posts.PostsService;
import lombok.RequiredArgsConstructor;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.util.List;

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

    @PutMapping("/api/v1/user/{id}/missionCheck")
    public void updateMissionDate(@PathVariable Long id){
        userService.updateMissionDate(id,LocalDate.now());
    }

    @GetMapping("/api/v1/user/totalOfToday")
    public int getTotalOfToday(){
        return userService.countMissionCheck();
    }

    @GetMapping("/api/v1/eduPosts/checkUsers")
    public void checkTodayMission(@RequestBody UserCheckRequestDto userCheckRequestDto, Model model){
        model.addAttribute("posts",userService.selectMissionCheck(userCheckRequestDto));
    }

    @PostMapping("/api/v1/eduPosts/registerAdminToUser")
    public List<User> registerAdminToUser(@RequestBody UserRegisterRequestDto userRegisterRequestDto){

        List<User> users=userService.registerAdminToUser(userRegisterRequestDto);
        System.out.println(users);
        return users;
    }


}
