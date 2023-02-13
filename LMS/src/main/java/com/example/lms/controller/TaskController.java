package com.example.lms.controller;


import com.example.lms.dto.TaskDetailsResponseDto;
import com.example.lms.dto.TaskDetailsRequestDto;
import com.example.lms.dto.course.update.CommentRequestDto;
import com.example.lms.dto.course.update.GradeRequestDto;
import com.example.lms.service.TaskService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequestMapping("/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;

    @PostMapping("/register")
    public ResponseEntity<TaskDetailsResponseDto> register(@RequestBody TaskDetailsRequestDto requestDto) {
        final var responseDto = taskService.register(requestDto);
        final var result = ResponseEntity.status(HttpStatus.OK).body(responseDto);
        return result;
    }

    @PutMapping("/grade")
    public ResponseEntity<String> updateGrade(@RequestBody GradeRequestDto requestDto) {
        log.info("updateGrade (PUT) with body - {}", requestDto);
        taskService.updateGrade(requestDto);
        return new ResponseEntity<>("Grade updated successfully", HttpStatus.OK);
    }

    @PutMapping("/comment")
    public ResponseEntity<String> updateComment(@RequestBody CommentRequestDto requestDto) {
        log.info("updateComment (PUT) with body - {}", requestDto);
        taskService.updateComment(requestDto);
        return new ResponseEntity<>("Grade updated successfully", HttpStatus.OK);
    }
}
