package ecodiary.server.domain.EduPosts;

import ecodiary.server.domain.Admin.AdminRepository;
import ecodiary.server.domain.User.UserRepository;
import ecodiary.server.global.OutputConst;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EduPostsService {
    private final EduPostsRepository eduPostsRepository;
    private final AdminRepository adminRepository;

    @Transactional
    public Long saveMission(EduPostsDto eduPostsDto){
        //List<User> users=userRepository.findByAdminId(adminId);
        Long adminId=eduPostsDto.getAdminId();
        adminRepository.findById(adminId).orElseThrow(() -> new IllegalArgumentException(OutputConst.NO_ADMIN + adminId));
        List<Long> usersNum=eduPostsRepository.findNum(adminId);
        Long max=0L;
        if (usersNum.size()!=0){
            max= Collections.max(usersNum);
        }
        return eduPostsRepository.save(eduPostsDto.toEntity(++max)).getId();

    }

    @Transactional(readOnly = true)
    public List<EduPosts> selectEduPosts(Long adminId){
        return eduPostsRepository.findByAdminId(adminId);
    }

    @Transactional
    public Long updateEduPosts(EduPostsResponseDto eduPostsResponseDto,Long id){
        EduPosts eduPosts=eduPostsRepository.findById(id).orElseThrow(() -> new IllegalArgumentException(OutputConst.NO_POST ));
        eduPosts.update(eduPostsResponseDto);
        return eduPosts.getId();
    }

    @Transactional(readOnly = true)
    public EduPosts selectEduPost(Long id){
        return eduPostsRepository.findById(id).orElseThrow(() -> new IllegalArgumentException(OutputConst.NO_POST ));
    }

    @Transactional
    public void deleteEduPosts(Long id){
        EduPosts eduPosts=eduPostsRepository.findById(id).orElseThrow(() -> new IllegalArgumentException(OutputConst.NO_POST));
        int num=eduPostsRepository.countByAdminId(eduPosts.getAdminId());
        if(num==eduPosts.getNum()){
            eduPostsRepository.deleteById(id);
        }else{
            throw new IllegalArgumentException(OutputConst.ONLY_FINAL_DELETABLE);
        }

    }

}
