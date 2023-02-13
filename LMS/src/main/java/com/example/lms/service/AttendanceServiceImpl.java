package com.example.lms.service;

import com.example.lms.domain.Attendance;
import com.example.lms.domain.Course;
import com.example.lms.domain.User;
import com.example.lms.dto.course.update.SessionInfoAttendanceDto;
import com.example.lms.repository.AttendanceRepository;
import com.example.lms.repository.CourseRepository;
import com.example.lms.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class AttendanceServiceImpl implements AttendanceService {

    private final AttendanceRepository attendanceRepository;

    private final CourseRepository courseRepository;

    private final UserRepository userRepository;

    @Override
    public void save(Course course, List<User> students) {
        for (User student : students)
            for (int i = 1; i <= course.getNumberOfSessions(); ++i) {
                Attendance attendance = new Attendance();
                attendance.setCourse(course);
                attendance.setUser(student);
                attendance.setSessionNumber(i);
                attendanceRepository.save(attendance);
            }
    }

    @Override
    public void setAttendance(SessionInfoAttendanceDto sessionInfoAttendanceDto) {
        Long courseId = courseRepository.findIdByUuid(sessionInfoAttendanceDto.getCourseUuid());
        Long userId = userRepository.findIdByUuid(sessionInfoAttendanceDto.getUserUuid());

        Attendance attendance =
                attendanceRepository.getAttendanceByCourse_IdAndUser_IdAndSessionNumber(
                        courseId,
                        userId,
                        Math.toIntExact(sessionInfoAttendanceDto.getSessionNumber()));

        attendance.setAttendedStatus(sessionInfoAttendanceDto.getIsPresent());
        attendanceRepository.save(attendance);
    }
}
