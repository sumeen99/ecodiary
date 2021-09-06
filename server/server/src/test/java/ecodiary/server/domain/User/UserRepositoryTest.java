package ecodiary.server.domain.User;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import static org.assertj.core.api.Assertions.assertThat;

@Transactional
@SpringBootTest
public class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;

    @Test
    public void save(){
        //given
        Long missionId=1L;

        //when
        User users=userRepository.save(User.builder().missionId(missionId).build());

        //then
        User user=userRepository.findById(users.getId()).get();
        assertThat(user).isEqualTo(users);


    }
}
