package ecodiary.server.domain.EduPosts;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class EduPostsDto {

    private Long adminId;
    private String mission;
    private String question;
    private String info;
    private String imgurl;

    @Builder
    public EduPostsDto(Long adminId,String mission, String question,String info, String imgurl){
        this.adminId=adminId;
        this.mission=mission;
        this.question=question;
        this.info=info;
        this.imgurl=imgurl;
    }

    public EduPosts toEntity(Long num){
        return EduPosts.builder().adminId(adminId).num(num).mission(mission).question(question).info(info).imgurl(imgurl).build();
    }
}
