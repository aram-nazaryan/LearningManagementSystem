package com.example.lms.service;

import com.example.lms.domain.Course;
import com.example.lms.domain.Task;
import com.example.lms.domain.User;
import com.example.lms.domain.UserTask;
import com.example.lms.dto.TaskDetailsRequestDto;
import com.example.lms.dto.TaskDetailsResponseDto;
import com.example.lms.dto.course.update.CommentRequestDto;
import com.example.lms.dto.course.update.GradeRequestDto;
import com.example.lms.dto.course.update.SessionInfoDto;
import com.example.lms.repository.CourseRepository;
import com.example.lms.repository.TaskRepository;
import com.example.lms.repository.UserRepository;
import com.example.lms.repository.UserTaskRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final ModelMapper modelMapper;
    private final CourseRepository courseRepository;
    private final UserRepository userRepository;
    private final UserTaskRepository userTaskRepository;

    @Override
    public TaskDetailsResponseDto register(TaskDetailsRequestDto requestDto) {
        Task task = modelMapper.map(requestDto, Task.class);

        Course course = courseRepository.findByUuid(requestDto.getCourse_uuid());
        task.setCourse(course);
        Task registeredTask = taskRepository.save(task);

        List<Long> userIds = courseRepository.findCourseUserIds(course.getId());
        for (Long id : userIds) {
            Optional<User> user = userRepository.findById(id);
            if (user.isPresent()) {
                User foundUser = user.get();
                if (foundUser.getRole().getName().equals("ROLE_STUDENT")) {
                    UserTask userTask = new UserTask();
                    userTask.setTask(task);
                    userTask.setUser(user.get());
                    userTaskRepository.save(userTask);
                }
            }
        }

        TaskDetailsResponseDto taskDetailsResponseDto = modelMapper.map(registeredTask, TaskDetailsResponseDto.class);
        return taskDetailsResponseDto;
    }

    @Override
    public Task getByUuid(String uuid) {
        return null;
    }

    @Override
    public void updateGrade(GradeRequestDto requestDto) {
        UserTask userTask = getUserTask(requestDto);
        userTask.setGrade(requestDto.getGrade());
        userTask.setPassedStatus(requestDto.getPassed());
        userTaskRepository.save(userTask);
    }

    @Override
    public void updateComment(CommentRequestDto requestDto) {
        UserTask userTask = getUserTask(requestDto);
        userTask.setComment(requestDto.getComment());
        userTaskRepository.save(userTask);
    }

    private UserTask getUserTask(SessionInfoDto requestDto) {
        Long courseId = courseRepository.findIdByUuid(requestDto.getCourseUuid());
        Task task = taskRepository.findTaskByNumberAndCourse_Id(requestDto.getSessionNumber(), courseId);
        Optional<User> user = userRepository.findUserByUuid(requestDto.getUserUuid());
        return userTaskRepository.getUserTaskByTask_IdAndUser_Id(task.getId(), user.get().getId());
    }
}
