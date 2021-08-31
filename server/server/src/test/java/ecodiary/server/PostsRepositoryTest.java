package ecodiary.server;

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

        Posts posts = postsList.get(0);
        assertThat(posts.getId()).isEqualTo(id);

    }
}
