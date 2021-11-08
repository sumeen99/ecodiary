package ecodiary.server.domain.User;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class UserRegisterRequestDto {
    private Long adminId;
    private Long userId;

    public UserRegisterRequestDto(Long adminId, Long userId) {
        this.adminId = adminId;
        this.userId = userId;
    }
}
