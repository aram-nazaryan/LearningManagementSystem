package com.example.lms.repository;

import com.example.lms.domain.Course;
import com.example.lms.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {

    Course findByUuid(String uuid);

    @Query("SELECT a.id FROM Course a WHERE a.uuid = :uuid")
    Long findIdByUuid(@Param("uuid") String uuid);

    @Query(value = "SELECT uc.user_id FROM courses c JOIN user_course uc ON c.id = uc.course_id " +
            "WHERE uc.course_id = :courseId", nativeQuery = true)
    List<Long> findCourseUserIds(Long courseId);

    @Query(value = "select uc.user_id from courses c join user_course uc ON uc.course_id = c.id  " +
            "where c.uuid = :uuid", nativeQuery = true)
    List<Long> getEnrolledUserIds(String uuid);

    @Query("SELECT c.id FROM Course c WHERE c.uuid IN :userUuids")
    List<Long> findAllIdsByUuids(List<String> userUuids);
}
