package ecodiary.server.domain;

import ecodiary.server.domain.User.User;
import ecodiary.server.domain.User.UserCheckRequestDto;
import ecodiary.server.domain.User.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    @RequestMapping("/users/save")
    public String usersSave(Model model, @RequestParam(value="userList",required = false) List<User> userList){
        System.out.println("성공2");
        model.addAttribute("users",userList);
        return "users-save";
    }


    @GetMapping("/users/check")
    public String usersCheck(@RequestBody UserCheckRequestDto userCheckRequestDto, Model model){
        model.addAttribute("posts",userService.selectMissionCheck(userCheckRequestDto));
        return "users-check";
    }
}

