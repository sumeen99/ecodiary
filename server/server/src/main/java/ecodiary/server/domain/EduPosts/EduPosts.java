package ecodiary.server.domain.EduPosts;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "eduDaily")
@Builder
public class EduPosts {

    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    @Column(nullable = false)
    private Long id;

    @Column(nullable = false,name = "admin_id")
    private Long adminId;

    @Column(nullable = false)
    private Long num;

    @Column(nullable = false)
    private String mission;

    @Column(nullable = false)
    private String question;

    private String info;
    private String imgurl;




}
