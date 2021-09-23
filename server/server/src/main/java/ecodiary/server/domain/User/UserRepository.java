package ecodiary.server.domain.User;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;

public interface UserRepository extends JpaRepository<User,Long> {
    int countByMissionDate(LocalDate missionDate);

}
