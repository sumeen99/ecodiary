package ecodiary.server.domain.User;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    int countByMissionDate(LocalDate missionDate);

    List<User> findByAdminId(Long adminId);


}
