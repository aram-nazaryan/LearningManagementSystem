package com.example.lms.repository;

import com.example.lms.domain.Attendance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AttendanceRepository extends JpaRepository<Attendance, Long> {

    Attendance getAttendanceByCourse_IdAndUser_IdAndSessionNumber(Long courseId, Long userId, Integer sessionNumber);

    @Query(value = "select attendance.present from attendance where course_id = :courseId and session_number = :sessionNumber" +
            " and user_id = :userId", nativeQuery = true)
    Boolean getStatus(Long courseId, Integer sessionNumber, Long userId);
}