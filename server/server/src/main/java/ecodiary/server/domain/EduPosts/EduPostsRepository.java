package ecodiary.server.domain.EduPosts;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EduPostsRepository extends JpaRepository<EduPosts,Long> {
List<EduPosts> findByAdminId(Long adminId);

    @Query(value = "SELECT num FROM edu_daily WHERE admin_id=:adminId",nativeQuery = true)
    List<Long> findNum(@Param("adminId")Long adminId);

    @Query(value = "SELECT * FROM edu_daily WHERE admin_id=:adminId and num=:num",nativeQuery = true)
    EduPosts findByAdminIdAndNum(@Param("adminId")Long adminId, @Param("num")Long num);

}
