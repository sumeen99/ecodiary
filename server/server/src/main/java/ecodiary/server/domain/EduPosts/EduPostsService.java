package ecodiary.server.domain.EduPosts;

import ecodiary.server.domain.Admin.AdminRepository;
import ecodiary.server.domain.User.User;
import ecodiary.server.domain.User.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EduPostsService {
    private final EduPostsRepository eduPostsRepository;
    private final UserRepository userRepository;
    private final AdminRepository adminRepository;

    @Transactional
    public Long saveMission(EduPostsDto eduPostsDto){
        //List<User> users=userRepository.findByAdminId(adminId);
        Long adminId=eduPostsDto.getAdminId();
        adminRepository.findById(adminId).orElseThrow(() -> new IllegalArgumentException("해당 관리자가 없습니다. id=" + adminId));
        List<Long> usersNum=userRepository.findNum(adminId);
        Long max=0L;
        if (usersNum.size()!=0){
            max= Collections.max(usersNum);
        }
        return eduPostsRepository.save(eduPostsDto.toEntity(++max)).getId();

    }

}
