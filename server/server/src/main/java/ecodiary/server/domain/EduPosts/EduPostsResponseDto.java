package ecodiary.server.domain.EduPosts;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;

@NoArgsConstructor
@Getter
public class EduPostsResponseDto {
    private String mission;
    private String question;
    private String info;
    private String imgurl;



}
