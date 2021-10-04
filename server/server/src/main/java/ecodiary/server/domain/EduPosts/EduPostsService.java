package ecodiary.server.domain.EduPosts;

import ecodiary.server.domain.Admin.AdminRepository;
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
    private final AdminRepository adminRepository;

    @Transactional
    public Long saveMission(EduPostsDto eduPostsDto){
        //List<User> users=userRepository.findByAdminId(adminId);
        Long adminId=eduPostsDto.getAdminId();
        adminRepository.findById(adminId).orElseThrow(() -> new IllegalArgumentException("해당 관리자가 없습니다. id=" + adminId));
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
        EduPosts eduPosts=eduPostsRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다." ));
        eduPosts.update(eduPostsResponseDto);
        return eduPosts.getId();
    }

    @Transactional(readOnly = true)
    public EduPosts selectEduPost(Long id){
        return eduPostsRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다." ));
    }

    @Transactional
    public void deleteEduPosts(Long id){
        EduPosts eduPosts=eduPostsRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다." ));
        int num=eduPostsRepository.countByAdminId(eduPosts.getAdminId());
        if(num==eduPosts.getNum()){
            eduPostsRepository.deleteById(id);
        }else{
            throw new IllegalArgumentException("마지막 게시글만 삭제 가능합니다.");
        }

    }

}
