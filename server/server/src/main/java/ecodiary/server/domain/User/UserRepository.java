package ecodiary.server.domain.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface UserRepository extends JpaRepository<User,Long> {
    int countByMissionDate(LocalDate missionDate);
    List<User> findByAdminId(Long adminId);

    @Query(value = "SELECT num FROM user WHERE admin_id = ?0",nativeQuery = true)
    List<Long> findNum(Long adminId);


}
