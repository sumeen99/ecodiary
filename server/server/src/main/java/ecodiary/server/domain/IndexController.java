package ecodiary.server.domain;

import ecodiary.server.domain.User.UserCheckRequestDto;
import ecodiary.server.domain.User.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RequiredArgsConstructor
@Controller
public class IndexController {
    private UserService userService;

    @GetMapping("/")
    public String index() {
        // model.addAttribute("posts", postsService.findAllDesc());
        return "index";
    }

    @GetMapping("/posts/save")
    public String postsSave(){
        return "posts-save";
    }

    //주미
    @GetMapping("/users/save")
    public String usersSave(){
        return "users-save";
    }

    @GetMapping("/users/check")
    public String usersCheck(@RequestBody UserCheckRequestDto userCheckRequestDto, Model model){
        model.addAttribute("posts",userService.selectMissionCheck(userCheckRequestDto));
        return "users-check";
    }
}

