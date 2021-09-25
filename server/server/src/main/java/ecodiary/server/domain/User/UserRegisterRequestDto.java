package ecodiary.server.domain.User;

public class UserRegisterRequestDto {
    private Long adminId;
    private Long userId;

    public UserRegisterRequestDto(Long adminId,Long userId){
        this.adminId=adminId;
        this.userId=userId;
    }
}
