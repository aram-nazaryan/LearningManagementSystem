package com.example.lms.service;

import com.example.lms.domain.Course;
import com.example.lms.domain.User;
import com.example.lms.dto.course.update.SessionInfoAttendanceDto;

import java.util.List;

public interface AttendanceService {

    void save(Course course, List<User> users);

//    List<Map<String, Object>> getUsersBySession(Long courseId, Integer sessionNumber);

    void setAttendance(SessionInfoAttendanceDto sessionInfoAttendanceDto);
}
