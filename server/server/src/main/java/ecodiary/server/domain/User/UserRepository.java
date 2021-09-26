package ecodiary.server.domain.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface UserRepository extends JpaRepository<User,Long> {
    int countByMissionDate(LocalDate missionDate);
    List<User> findByAdminId(Long adminId);

    @Query(value = "SELECT num FROM edu_daily WHERE admin_id=:adminId",nativeQuery = true)
    List<Long> findNum(@Param("adminId")Long adminId);


}
