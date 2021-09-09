package ecodiary.server.domain.User;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public Long createUser(){
        User user=userRepository.save(User.builder().missionId(null).build()); //Builder를 사용 or DTO를 사용(client에서 주는게 없어서 DTO를 사용해야 하는지 잘 모르겠음)
        return user.getId();
    }

    @Transactional
    public void updateMissionId(Long userId){//수정은 save or findbyId를 통해 entity에 update라는 함수를 넣으면 된다.. 뭐가 더 좋을까?
        User user= userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("해당 유저가 없습니다. id=" + userId));
        Long currentMissionId=user.getMissionId();
        if (currentMissionId==null){
            currentMissionId=0L;
        }
        userRepository.save(User.builder().id(userId).missionId(++currentMissionId).build());
    }

    @Transactional(readOnly = true)
    public Long selectMissionId(Long userId){
        User user= userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("해당 유저가 없습니다. id=" + userId));
        return user.getMissionId();
    }



}
