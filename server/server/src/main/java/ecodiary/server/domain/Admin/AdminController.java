package ecodiary.server.domain.Admin;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequiredArgsConstructor
public class AdminController {


    private final AdminService adminService;

    @ResponseBody
    @GetMapping("/api/v1/admin/check/{adminId}")
    public Long checkAdmin(@PathVariable Long adminId){
        return adminService.checkAdmin(adminId);

    }
}
