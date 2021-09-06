package ecodiary.server.domain.Posts;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class PostsService {

    private final PostsRepository postsRepository;

    public String findMission(Long id){
        Posts posts=postsRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 미션이 없습니다. id=" + id));
        return posts.getMission();
    }

    public String findQuestion(Long id){
        Posts posts=postsRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 미션이 없습니다. id=" + id));
        return posts.getQuestion();
    }

    public String findInfo(Long id){
        Posts posts=postsRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 미션이 없습니다. id=" + id));
        return posts.getInfo();
    }

}
