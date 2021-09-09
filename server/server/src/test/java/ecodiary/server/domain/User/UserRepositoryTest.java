package ecodiary.server.domain.User;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
        User user=userRepository.findById(users.getId()).orElseThrow(() -> new IllegalArgumentException("그런 사람없습니다"));;
        assertThat(user).isEqualTo(users);

    }

    @Test
    public void findAll(){
        //given
        Long missionId=2L;
        userRepository.save(User.builder().missionId(missionId).build());

        //when
        List<User> users=userRepository.findAll();

        //then
        assertThat(users.get(0).getId()).isEqualTo(missionId);
    }
}
