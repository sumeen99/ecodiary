package ecodiary.server.domain.Posts;

import ecodiary.server.domain.EduPosts.EduPosts;
import ecodiary.server.domain.EduPosts.EduPostsRepository;
import ecodiary.server.domain.User.User;
import ecodiary.server.domain.User.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class PostsService {

    private final PostsRepository postsRepository;
    private final UserRepository userRepository;
    private final EduPostsRepository eduPostsRepository;

    @Transactional(readOnly = true)
    public String findMission(Long id){
        User user=userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 유저가 없습니다. id=" + id));
        Long missionId=user.getMissionId();
        Long adminId=user.getAdminId();
        System.out.println(missionId);
        System.out.println(adminId);
        if(checkAdmin(adminId)){
            Posts posts=postsRepository.findById(missionId).orElseThrow(() -> new IllegalArgumentException("해당 미션이 없습니다. id="+id));
            System.out.println(posts);
            return posts.getMission();
        }
        else{
            EduPosts eduPosts=eduPostsRepository.findByAdminIdAndNum(adminId,missionId);
            System.out.println(eduPosts);
//            checkNull(eduPosts,userId);
            return eduPosts.getMission();
        }

    }

    @Transactional(readOnly = true)
    public String findQuestion(Long id){
        User user=userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 유저가 없습니다. id=" + id));
        Long missionId=user.getMissionId();
        Long adminId=user.getAdminId();
        if(checkAdmin(adminId)){
            Posts posts=postsRepository.findById(missionId).orElseThrow(() -> new IllegalArgumentException("해당 미션이 없습니다. id="+id));
            return posts.getQuestion();
        }
        else{
            EduPosts eduPosts=eduPostsRepository.findByAdminIdAndNum(adminId,missionId);
//            checkNull(eduPosts,userId);
            return eduPosts.getQuestion();
        }
    }

    @Transactional(readOnly = true)
    public String findInfo(Long id){
        User user=userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 유저가 없습니다. id=" + id));
        Long missionId=user.getMissionId();
        Long adminId=user.getAdminId();
        if(checkAdmin(adminId)){
            Posts posts=postsRepository.findById(missionId).orElseThrow(() -> new IllegalArgumentException("해당 미션이 없습니다. id="+id));
            return posts.getInfo();
        }
        else{
            EduPosts eduPosts=eduPostsRepository.findByAdminIdAndNum(adminId,missionId);
//            checkNull(eduPosts,userId);
            return eduPosts.getInfo();
        }
    }

    @Transactional(readOnly = true)
    public String findImgUrl(Long id){
        User user=userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 유저가 없습니다. id=" + id));
        Long missionId=user.getMissionId();
        Long adminId=user.getAdminId();
        if(checkAdmin(adminId)){
            Posts posts=postsRepository.findById(missionId).orElseThrow(() -> new IllegalArgumentException("해당 미션이 없습니다. id="+id));
            return posts.getImgurl();
        }
        else{
            EduPosts eduPosts=eduPostsRepository.findByAdminIdAndNum(adminId,missionId);
//            checkNull(eduPosts,userId);
            return eduPosts.getImgurl();
        }
    }

    public boolean checkAdmin(Long adminId){
        return adminId == null;
    }
//    public void checkNull(EduPosts eduPosts,Long userId){//이거 null여도 그냥 오류 안걸리게 하는게 지금 나을듯 교육시 해당 일수 끝날경우를 생각안함
//        if (eduPosts==null){
//        throw new IllegalArgumentException("해당 미션이 없습니다. id="+userId);
//        }
//    }



}
