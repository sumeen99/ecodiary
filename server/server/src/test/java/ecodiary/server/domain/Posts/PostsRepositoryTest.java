package ecodiary.server.domain.Posts;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
public class PostsRepositoryTest {

    @Autowired
    PostsRepository postsRepository;

    @Test
    public void findAll(){

        //given
        Long id=1L;
        String title="오늘 하루 일회용품 대신 텀블러 써보기";

        //when
        List<Posts> postsList = postsRepository.findAll();

        //then
        Posts posts = postsList.get(0);
        assertThat(posts.getId()).isEqualTo(id);
        assertThat(posts.getMission()).isEqualTo(title);

    }

    @Test
    public void save(){
        //given
        String mission="오늘의 미션";
        String question="오늘의 질문";
        String info="오늘의 정보";

        //when
        Posts post=postsRepository.save(Posts.builder().mission(mission).question(question).info(info).build());

        //then
        Posts posts=postsRepository.findById(post.getId()).orElseThrow(() -> new IllegalArgumentException("그런 미션없습니다"));
        assertThat(post).isEqualTo(posts);

    }




}
