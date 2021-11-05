package ecodiary.server.domain.User;

import ecodiary.server.domain.Admin.AdminRepository;
import ecodiary.server.global.OutputConst;
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
    private final AdminRepository adminRepository;

    @Transactional
    public Long createUser(){
        User user=userRepository.save(User.builder().missionId(OutputConst.resetNum).build());
        return user.getId();
    }

    @Transactional
    public void updateMissionId(Long userId){
        User user= userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException(OutputConst.NO_USER + userId));
        Long currentMissionId=user.getMissionId();
        user.updateMissionId(++currentMissionId);
    }

    @Transactional(readOnly = true)
    public Long selectMissionId(Long userId){
        User user= userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException(OutputConst.NO_USER + userId));
        return user.getMissionId();
    }

    @Transactional
    public void updateMissionDate(Long userId, LocalDate missionDate){
        User user=userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException(OutputConst.NO_USER + userId));
        user.updateDate(missionDate);
    }

    @Transactional(readOnly = true)
    public int countMissionCheck(){
        return userRepository.countByMissionDate(LocalDate.now());
    }

    @Transactional(readOnly = true)
    public List<UserResponseDto> selectMissionCheck(Long admin){
        List<UserResponseDto> userResponseDtoList=new ArrayList<>();
        userRepository.findByAdminId(admin).forEach(i->{
            if (i.getMissionDate() == null){
                userResponseDtoList.add(new UserResponseDto(i.getId(),false));
            }
            else if(i.getMissionDate().equals(LocalDate.now())){
                userResponseDtoList.add(new UserResponseDto(i.getId(),true));
            }
            else{
                userResponseDtoList.add(new UserResponseDto(i.getId(),false));
            }
        });
        return userResponseDtoList;
    }

    @Transactional
    public List<User> registerAdminToUser(UserRegisterRequestDto userRegisterRequestDto){
        Long adminId=userRegisterRequestDto.getAdminId();
        Long userId= userRegisterRequestDto.getUserId();
        adminRepository.findById(adminId).orElseThrow(() -> new IllegalArgumentException(OutputConst.NO_ADMIN + userId));
        User user=userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException(OutputConst.NO_USER + userId));
        user.updateAdminId(adminId,OutputConst.resetNum);
        return userRepository.findByAdminId(adminId);
    }

    @Transactional(readOnly = true)
    public List<User> selectUserList(Long adminId){
        return userRepository.findByAdminId(adminId);
    }



}
