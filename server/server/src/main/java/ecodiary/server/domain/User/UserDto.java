package ecodiary.server.domain.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
public class UserDto {
    //private Long id
    private Long missionId;

    public void putMissionId(Long missionId){
        this.missionId=missionId;
    }

    public User postUser(){
        return User.builder().missionId(null).build();
    }

    


}
