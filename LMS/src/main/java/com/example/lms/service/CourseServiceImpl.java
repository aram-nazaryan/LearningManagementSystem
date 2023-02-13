package com.example.lms.service;

import com.example.lms.domain.*;
import com.example.lms.dto.CourseRegisterRequestDto;
import com.example.lms.dto.CourseShortDetailsResponseDto;
import com.example.lms.dto.course.AttendanceDto;
import com.example.lms.dto.course.CourseDetailResponseDto;
import com.example.lms.dto.course.HomeworkDto;
import com.example.lms.dto.course.SessionDto;
import com.example.lms.repository.*;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;
    private final AttendanceRepository attendanceRepository;
    private final TaskRepository taskRepository;
    private final ModelMapper modelMapper;
    private final UserRepository userRepository;
    private final AttendanceService attendanceService;
    private final UserTaskRepository userTaskRepository;

    @Override
    public CourseShortDetailsResponseDto register(CourseRegisterRequestDto requestDto) {
        Course course = modelMapper.map(requestDto, Course.class);
        List<String> userUuids = requestDto.getUserUuids();
        List<User> users = new ArrayList<>();
        for (String uuid : userUuids) {
            Optional<User> user = userRepository.findUserByUuid(uuid);
            user.ifPresent(users::add);
        }
        course.setUsers(users);
        courseRepository.save(course);

        attendanceService.save(course, users);

        return modelMapper.map(course, CourseShortDetailsResponseDto.class);
    }

    @Override
    public List<CourseShortDetailsResponseDto> getAll() {
        List<Course> courses = courseRepository.findAll();
        List<CourseShortDetailsResponseDto> responseDtos = new ArrayList<>();
        modelMapper.map(courses, responseDtos);
        for (Course course : courses) {
            responseDtos.add(modelMapper.map(course, CourseShortDetailsResponseDto.class));
        }
        return responseDtos;
    }

    @Override
    public CourseDetailResponseDto getByUuid(String uuid) {
        List<Long> userIds = courseRepository.getEnrolledUserIds(uuid);
        List<User> users = userRepository.findAllById(userIds);
        Course course = courseRepository.findByUuid(uuid);

        CourseDetailResponseDto courseDetailResponseDto = new CourseDetailResponseDto();

        courseDetailResponseDto.setUuid(course.getUuid());
        courseDetailResponseDto.setName(course.getName());
        courseDetailResponseDto.setStartDate(course.getStartDate());
        courseDetailResponseDto.setEndDate(course.getEndDate());
        courseDetailResponseDto.setNumberOfSessions(course.getNumberOfSessions());

        for (User user: users) {
            if (user.getRole() == Role.ROLE_TRAINER) {
                courseDetailResponseDto.setTrainerUuid(user.getUuid());
                courseDetailResponseDto.setTrainerFirstName(user.getFirstName());
                courseDetailResponseDto.setTrainerLastName(user.getLastName());
                users.remove(user);
                break;
            }
        }

        List<SessionDto> sessionDtos = new ArrayList<>();
        for (int i = 1; i <= course.getNumberOfSessions(); i++) {
            SessionDto sessionDto = new SessionDto();
            List<AttendanceDto> attendanceDtos = new ArrayList<>();
            for (User user: users) {
                AttendanceDto attendanceDto = new AttendanceDto();
                attendanceDto.setUuid(user.getUuid());
                attendanceDto.setFirstName(user.getFirstName());
                attendanceDto.setLastName(user.getLastName());
                attendanceDto.setSessionNumber(i);
                attendanceDto.setPresent(attendanceRepository.getStatus(course.getId(), i, user.getId()));
                attendanceDtos.add(attendanceDto);
            }

            List<HomeworkDto> homeworkDtos = new ArrayList<>();
            for (User user: users) {
                HomeworkDto homeworkDto = new HomeworkDto();
                homeworkDto.setUuid(user.getUuid());
                homeworkDto.setNumber((long) i);
                homeworkDto.setFirstName(user.getFirstName());
                homeworkDto.setLastName(user.getLastName());

                Task task = taskRepository.findTaskByNumberAndCourse_Id((long) i, course.getId());
                if (task != null) {
                    homeworkDto.setType(task.getTaskType().toString());

                    UserTask userTask = userTaskRepository.getUserTaskByTask_IdAndUser_Id(task.getId(), user.getId());
                    homeworkDto.setComment(userTask.getComment());
                    homeworkDto.setGrade(userTask.getGrade());
                    homeworkDto.setPassed(userTask.getPassedStatus());
                    homeworkDtos.add(homeworkDto);
                }

            }
            sessionDto.setHomeworks(homeworkDtos);
            sessionDto.setAttendance(attendanceDtos);
            sessionDtos.add(sessionDto);
        }

        courseDetailResponseDto.setSessions(sessionDtos);
        return courseDetailResponseDto;
    }

}
