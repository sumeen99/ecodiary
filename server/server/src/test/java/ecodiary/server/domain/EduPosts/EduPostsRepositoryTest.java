package ecodiary.server.domain.EduPosts;

import ecodiary.server.domain.User.User;
import ecodiary.server.domain.User.UserRepository;
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
    public void save(){
        //given
        Long adminId=1L;

        //when
        Long maxNum= eduPostsRepository.findMaxNum(adminId);

        //then
        assertThat(maxNum).isEqualTo(2L);

    }
}
