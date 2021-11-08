package ecodiary.server.domain.Posts;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PostsDto {
    private Long id;
    private String mission;
    private String question;
    private String info;


}
