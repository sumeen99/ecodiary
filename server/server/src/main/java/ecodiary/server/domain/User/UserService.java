package ecodiary.server.domain.User;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public Long createUser(){
        User user=userRepository.save(User.builder().missionId(1L).build()); //Builder를 사용 or DTO를 사용(client에서 주는게 없어서 DTO를 사용해야 하는지 잘 모르겠음)
        return user.getId();
    }

    @Transactional
    public void updateMissionId(Long userId){//수정은 save or findbyId를 통해 entity에 update라는 함수를 넣으면 된다.. 뭐가 더 좋을까?
        User user= userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("해당 유저가 없습니다. id=" + userId));
        Long currentMissionId=user.getMissionId();
        userRepository.save(User.builder().id(userId).missionId(++currentMissionId).build());
    }

    @Transactional(readOnly = true)
    public Long selectMissionId(Long userId){
        User user= userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("해당 유저가 없습니다. id=" + userId));
        return user.getMissionId();
    }

    @Transactional
    public void updateMissionDate(Long userId, LocalDate missionDate){
        User user=userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("해당 유저가 없습니다. id=" + userId));
        user.updateDate(missionDate);
        //userRepository.save(User.builder().id(userId).missionId(user.getMissionId()).missionDate(missionDate).build());
    }

    @Transactional(readOnly = true)
    public int countMissionCheck(){
        //Date date=Date.valueOf(LocalDate.now());
        return userRepository.countByMissionDate(LocalDate.now());
    }

    @Transactional(readOnly = true)
    public List<UserResponseDto> selectMissionCheck(UserCheckRequestDto userCheckRequestDto){
//        List<User> users=userRepository.findByAdminId(admin);
//        users.stream().filter(t-> t.getMissionDate().equals(LocalDate.now())).map(t->new UserResponseDto(t.getId(),true)).collect(Collectors.toList());
        Long admin=userCheckRequestDto.getAdminId();
        List<UserResponseDto> userResponseDtoList=new ArrayList<>();
        userRepository.findByAdminId(admin).forEach(i->{
            if(i.getMissionDate().equals(LocalDate.now())){
                userResponseDtoList.add(new UserResponseDto(i.getId(),true));
            }
            else{
                userResponseDtoList.add(new UserResponseDto(i.getId(),false));
            }
        });
        return userResponseDtoList;

    }

    @Transactional
    public List<User> registerAdminToUser(Long adminId,Long userId){
        User user=userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("해당 유저가 없습니다. id=" + userId));
        user.updateAdminId(adminId);
        return userRepository.findByAdminId(adminId);
    }



}
