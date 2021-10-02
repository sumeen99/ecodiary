package ecodiary.server.domain.User;

import lombok.Getter;

@Getter

public class UserCheckRequestDto {
    private Long adminId;

    public UserCheckRequestDto(Long adminId){
        this.adminId=adminId;
    }

}
