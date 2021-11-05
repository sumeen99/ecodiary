package ecodiary.server.domain.EduPosts;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
public class EduPostsRepositoryTest {
    @Autowired
    EduPostsRepository eduPostsRepository;

    @Test
    public void findMaxNum(){
        //given
        Long adminId=1L;

        //when
        Long maxNum= eduPostsRepository.findMaxNum(adminId);

        //then
        assertThat(maxNum).isEqualTo(2L);

    }
}
