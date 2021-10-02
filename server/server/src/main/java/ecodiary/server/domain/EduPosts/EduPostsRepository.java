package ecodiary.server.domain.EduPosts;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EduPostsRepository extends JpaRepository<EduPosts,Long> {
List<EduPosts> findByAdminId(Long adminId);
}
