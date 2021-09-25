package ecodiary.server.domain.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user")
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @Column(name="mission_id",nullable = false)
    private Long missionId;

    @Column(name="mission_date")
    private LocalDate missionDate;

    @Column(name = "admin_id")
    private Long adminId;

    public void updateDate(LocalDate missionDate){
        this.missionDate=missionDate;
    }
    public void updateAdminId(Long adminId){
        this.adminId=adminId;
    }

}
