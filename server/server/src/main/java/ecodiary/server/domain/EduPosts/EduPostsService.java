package ecodiary.server.domain.EduPosts;

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

    @Transactional
    public void saveMission(EduPostsDto eduPostsDto){
        //List<User> users=userRepository.findByAdminId(adminId);
        List<Long> usersNum=userRepository.findNum(eduPostsDto.getAdminId());
        Long max= Collections.max(usersNum);
        eduPostsRepository.save(eduPostsDto.toEntity(++max));

    }

}
