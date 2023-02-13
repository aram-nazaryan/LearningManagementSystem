package com.example.lms.service;

import com.example.lms.domain.Course;
import com.example.lms.domain.Task;
import com.example.lms.domain.User;
import com.example.lms.domain.UserTask;
import com.example.lms.dto.feedback.CourseFeedbackDto;
import com.example.lms.dto.feedback.FeedbackRequestDto;
import com.example.lms.dto.feedback.FeedbackResponseDto;
import com.example.lms.dto.feedback.TaskFeedbackDto;
import com.example.lms.repository.CourseRepository;
import com.example.lms.repository.TaskRepository;
import com.example.lms.repository.UserRepository;
import com.example.lms.repository.UserTaskRepository;
import com.example.lms.util.PDFGenerator;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender javaMailSender;
    private final UserRepository userRepository;
    private final CourseRepository courseRepository;
    private final TaskRepository taskRepository;
    private final UserTaskRepository userTaskRepository;
    private final PDFGenerator pdfGenerator;
    @Value("${spring.mail.username}")
    private String sender;

    private FeedbackResponseDto collectFeedback(FeedbackRequestDto feedbackRequestDto) {
        FeedbackResponseDto feedbackResponseDto = new FeedbackResponseDto();

        Optional<User> user = userRepository.findUserByUuid(feedbackRequestDto.getUserUuid());

        feedbackResponseDto.setUserUuid(feedbackRequestDto.getUserUuid());
        feedbackResponseDto.setUserFirstName(user.orElseThrow().getFirstName());
        feedbackResponseDto.setUserLastName(user.orElseThrow().getLastName());
        feedbackResponseDto.setUserEmail(
                userRepository.findUserByUuid(feedbackRequestDto.getUserUuid()).orElseThrow().getUsername()
        );
        feedbackResponseDto.setCourseFeedbackDtos(collectCourseFeedbackList(feedbackRequestDto));

        Long userID = userRepository.findIdByUuid(feedbackRequestDto.getUserUuid());
        List<Long> courseIds = courseRepository.findAllIdsByUuids(feedbackRequestDto.getCourseUuids());
        Double attendancePercentage = userTaskRepository.findAttendancePercentageByCourseIdAndUserId(userID, courseIds);
        feedbackResponseDto.setAttendancePercentage(String.format("%.1f", attendancePercentage));

        return feedbackResponseDto;
    }

    private List<CourseFeedbackDto> collectCourseFeedbackList(FeedbackRequestDto feedbackRequestDto) {
        List<CourseFeedbackDto> courseFeedbackDtos = new ArrayList<>();

        for (String courseUuid : feedbackRequestDto.getCourseUuids()) {
            CourseFeedbackDto courseFeedbackDto = new CourseFeedbackDto();

            Course course = courseRepository.findByUuid(courseUuid);
            courseFeedbackDto.setName(course.getName());
            courseFeedbackDto.setTaskFeedbackDtos(
                    collectTaskFeedBackDtos(
                            course.getId(), userRepository.findIdByUuid(feedbackRequestDto.getUserUuid())
                    )
            );

            courseFeedbackDtos.add(courseFeedbackDto);
        }

        return courseFeedbackDtos;
    }

    private List<TaskFeedbackDto> collectTaskFeedBackDtos(Long courseId, Long userId) {
        List<TaskFeedbackDto> taskFeedbackDtos = new ArrayList<>();

        List<Task> courseTasks = taskRepository.findAllByCourse_Id(courseId);

        for (Task task : courseTasks) {
            TaskFeedbackDto taskFeedbackDto = new TaskFeedbackDto();
            taskFeedbackDto.setType(task.getTaskType().toString());
            taskFeedbackDto.setNumber(task.getNumber());

            UserTask userTask = userTaskRepository.getUserTaskByTask_IdAndUser_Id(task.getId(), userId);
            taskFeedbackDto.setPassed(userTask.getPassedStatus() == null ? "" : String.valueOf(userTask.getPassedStatus()));
            taskFeedbackDto.setGrade(userTask.getGrade() == null ? 0 : userTask.getGrade());
            taskFeedbackDto.setComment(userTask.getComment() == null ? "" : userTask.getComment());

            taskFeedbackDtos.add(taskFeedbackDto);
        }

        return taskFeedbackDtos;
    }

    @Override
    public void sendMailWithAttachment(FeedbackResponseDto details) {
        // Creating a mime message
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper;

        pdfGenerator.createPDF(details);

        try {
            // Setting multipart as true for attachments to
            // be send
            mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
            mimeMessageHelper.setFrom(sender);
            mimeMessageHelper.setTo(details.getUserEmail());
            mimeMessageHelper.setText("Hello " + details.getUserFirstName() + ", \n Find the feedback of current " +
                    "courses attached below.");
            mimeMessageHelper.setSubject("Feedback");

            // Adding the attachment
            FileSystemResource file = new FileSystemResource(
                    new File("./feedbackDir/" + details.getUserFirstName() + " " + details.getUserLastName() +
                            ".pdf")
            );

            mimeMessageHelper.addAttachment(file.getFilename(), file);

            // Sending the mail
            javaMailSender.send(mimeMessage);

            file.getFile().delete();

            log.info("Email sent successfully");
        } catch (MessagingException e) {
            // Display message when exception occurred
            log.info("Email is not sent");
        }
    }

    @Override
    public FeedbackResponseDto getFeedback(FeedbackRequestDto feedbackRequestDto) {
        return collectFeedback(feedbackRequestDto);
    }
}
