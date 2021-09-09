package ecodiary.server.domain.User;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class UserController {

    private final UserService userService;

    @GetMapping("/api/v1/user/{id}/missionId")
    public Long getMissionId(@PathVariable Long id){
        return userService.selectMissionId(id);
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
