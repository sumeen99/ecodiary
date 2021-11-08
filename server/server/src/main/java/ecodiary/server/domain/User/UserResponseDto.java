package ecodiary.server.domain.User;

import lombok.Getter;

@Getter
public class UserResponseDto {
    private Long id;
    private boolean check;

    public UserResponseDto(Long id, boolean check) {
        this.id = id;
        this.check = check;
    }

}
