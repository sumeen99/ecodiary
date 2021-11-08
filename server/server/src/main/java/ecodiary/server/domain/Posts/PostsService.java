package ecodiary.server.domain.Posts;

import ecodiary.server.domain.EduPosts.EduPosts;
import ecodiary.server.domain.EduPosts.EduPostsRepository;
import ecodiary.server.domain.User.User;
import ecodiary.server.domain.User.UserRepository;
import ecodiary.server.global.OutputConst;
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
    public String findMission(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException(OutputConst.NO_USER + id));
        Long missionId = user.getMissionId();
        Long adminId = user.getAdminId();
        if (checkAdmin(adminId)) {
            Posts posts = postsRepository.findById(missionId).orElseThrow(() -> new IllegalArgumentException(OutputConst.NO_MISSION + id));
            return posts.getMission();
        } else {
            EduPosts eduPosts = eduPostsRepository.findByAdminIdAndNum(adminId, missionId);
            return eduPosts.getMission();
        }

    }

    @Transactional(readOnly = true)
    public String findQuestion(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException(OutputConst.NO_USER + id));
        Long missionId = user.getMissionId();
        Long adminId = user.getAdminId();
        if (checkAdmin(adminId)) {
            Posts posts = postsRepository.findById(missionId).orElseThrow(() -> new IllegalArgumentException(OutputConst.NO_MISSION + id));
            return posts.getQuestion();
        } else {
            EduPosts eduPosts = eduPostsRepository.findByAdminIdAndNum(adminId, missionId);
            return eduPosts.getQuestion();
        }
    }

    @Transactional(readOnly = true)
    public String findInfo(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException(OutputConst.NO_USER + id));
        Long missionId = user.getMissionId();
        Long adminId = user.getAdminId();
        if (checkAdmin(adminId)) {
            Posts posts = postsRepository.findById(missionId).orElseThrow(() -> new IllegalArgumentException(OutputConst.NO_MISSION + id));
            return posts.getInfo();
        } else {
            EduPosts eduPosts = eduPostsRepository.findByAdminIdAndNum(adminId, missionId);
            return eduPosts.getInfo();
        }
    }

    @Transactional(readOnly = true)
    public String findImgUrl(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException(OutputConst.NO_USER + id));
        Long missionId = user.getMissionId();
        Long adminId = user.getAdminId();
        if (checkAdmin(adminId)) {
            Posts posts = postsRepository.findById(missionId).orElseThrow(() -> new IllegalArgumentException(OutputConst.NO_MISSION + id));
            return posts.getImgurl();
        } else {
            EduPosts eduPosts = eduPostsRepository.findByAdminIdAndNum(adminId, missionId);
            return eduPosts.getImgurl();
        }
    }

    public boolean checkAdmin(Long adminId) {
        return adminId == null;
    }


}
