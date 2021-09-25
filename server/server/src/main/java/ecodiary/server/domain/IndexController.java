package ecodiary.server.domain;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {

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
    public String usersCheck(){
        return "users-check";
    }
}

