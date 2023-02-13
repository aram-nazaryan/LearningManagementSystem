package com.example.lms.repository;

import com.example.lms.domain.UserTask;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface UserTaskRepository extends JpaRepository<UserTask, Long> {

    UserTask getUserTaskByTask_IdAndUser_Id(Long taskId, Long userId);

    @Query(value = "SELECT (COUNT(CASE WHEN a.present = true THEN 1 ELSE NULL END) * 100.0) / COUNT(CASE WHEN a.present IS NOT NULL THEN 1 ELSE NULL END) " +
            "FROM Attendance a WHERE a.course_id in :courseIds AND a.user_id = :userId", nativeQuery = true)
    Double findAttendancePercentageByCourseIdAndUserId(Long userId, List<Long> courseIds);
}
