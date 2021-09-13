package ecodiary.server.domain.User;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class UserDto {
    private final Long missionId;
    private final String mission;


}
