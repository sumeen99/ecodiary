package ecodiary.server.domain.Posts;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.net.URL;

@RequiredArgsConstructor
@Service
public class PostsService {

    private final PostsRepository postsRepository;

    @Transactional(readOnly = true)
    public String findMission(Long id){
        Posts posts=postsRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 미션이 없습니다. id=" + id));
        return posts.getMission();
    }

    @Transactional(readOnly = true)
    public String findQuestion(Long id){
        Posts posts=postsRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 미션이 없습니다. id=" + id));
        return posts.getQuestion();
    }

    @Transactional(readOnly = true)
    public String findInfo(Long id){
        Posts posts=postsRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 미션이 없습니다. id=" + id));
        return posts.getInfo();
    }

    @Transactional(readOnly = true)
    public String findImgUrl(Long id){
        Posts posts=postsRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 미션이 없습니다. id=" + id));
        return posts.getImgurl();
    }


}
