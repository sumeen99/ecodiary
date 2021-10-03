package ecodiary.server.domain;

import ecodiary.server.domain.EduPosts.EduPosts;
import ecodiary.server.domain.EduPosts.EduPostsService;
import ecodiary.server.domain.User.User;
import ecodiary.server.domain.User.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class IndexController {
    private final UserService userService;
    private final EduPostsService eduPostsService;
    private final String url="ec2-3-37-141-187.ap-northeast-2.compute.amazonaws.com";
   // private final String url="localhost";


    @GetMapping("/")
    public String index() {
        // model.addAttribute("posts", postsService.findAllDesc());
        return "index";
    }

    @GetMapping("/admin/{adminId}/posts/save")
    public String postsSave(Model model,@PathVariable Long adminId){
        model.addAttribute("adminId",adminId);
        model.addAttribute("url",url);
        return "posts-save";
    }

    //주미
    @RequestMapping("/admin/{adminId}/users/save")
    public String usersSave(Model model,@PathVariable Long adminId){
        model.addAttribute("adminId",adminId);
        model.addAttribute("url",url);
        return "users-save";
    }


    @GetMapping("/admin/{adminId}/home")
    public String home(Model model, @PathVariable Long adminId){
        model.addAttribute("users",userService.selectMissionCheck(adminId));
       model.addAttribute("url",url);
        model.addAttribute("adminId",adminId);
        return "home";
    }

    @GetMapping("/admin/{adminId}/posts/list")
    public String postsList(@PathVariable Long adminId, Model model){
        model.addAttribute("posts",eduPostsService.selectEduPosts(adminId));
        model.addAttribute("url",url);
        model.addAttribute("adminId",adminId);
        return "posts-list";
    }

    @GetMapping("admin/{adminId}/posts/update/{id}")
    public String postsUpdate(@PathVariable Long adminId, @PathVariable Long id,Model model){
        EduPosts eduPosts =eduPostsService.selectEduPost(id);
        model.addAttribute("adminId",adminId);
        model.addAttribute("post",eduPosts);
        model.addAttribute("url",url);
        return "posts-update";
    }
///////////////////////////////

    @GetMapping("users/save/{id}")
    public String usersList(Model model,@PathVariable Long id){
        List<User> users=userService.selectUserList(id);
        model.addAttribute("users",users);
        return "users-list";
    }


    @GetMapping("/users/check/{id}")
    public String usersCheckList(@PathVariable Long id, Model model){
        model.addAttribute("posts",userService.selectMissionCheck(id));
        return "users-checklist";
    }

    @GetMapping("/users/check")
    public String userCheck(){
        return "users-check";
    }


}

