package ecodiary.server;

import ecodiary.server.domain.Posts.Posts;
import ecodiary.server.domain.Posts.PostsRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

@SpringBootTest
public class PostsRepositoryTest {

    @Autowired
    PostsRepository postsRepository;

    @Test
    public void all(){
        List<Posts> postsList = postsRepository.findAll();
        Long id=1L;
        String title="오늘 하루 일회용품 대신 텀블러 써보기";

        Posts posts = postsList.get(0);
        assertThat(posts.getId()).isEqualTo(id);
        assertThat(posts.getMission()).isEqualTo(title);

    }
}
