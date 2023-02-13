package com.example.lms.repository;

import com.example.lms.domain.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    Task findTaskByNumberAndCourse_Id(Long number, Long courseId);

    Long findIdByNumberAndCourse_Id(Long number, Long courseId);

    List<Task> findAllByCourse_Id(Long course_id);
}
