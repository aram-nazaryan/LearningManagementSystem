package com.example.lms.controller;

import com.example.lms.dto.course.CourseDetailResponseDto;
import com.example.lms.dto.CourseRegisterRequestDto;
import com.example.lms.dto.CourseShortDetailsResponseDto;
import com.example.lms.service.CourseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/dashboard")
@RequiredArgsConstructor
public class CourseController {

    private final CourseService courseService;

    @GetMapping("/courses")
    public ResponseEntity<List<CourseShortDetailsResponseDto>> getAllCourses() {
        log.info("Start getting all courses");
        final var responseDto = courseService.getAll();
        final var result = ResponseEntity.status(HttpStatus.OK).body(responseDto);
        log.info("Finished getting all courses");
        return result;
    }

    @GetMapping("/courses/{uuid}")
    public ResponseEntity<CourseDetailResponseDto> getCourseByUuid(@PathVariable("uuid") String uuid) {
        log.info("Start getting course by uuid: {}", uuid);
        final var responseDto = courseService.getByUuid(uuid);
        final var result = ResponseEntity.status(responseDto.getStatus()).body(responseDto);
        log.info("Finished getting course by uuid: {}", uuid);
        return result;
    }

    @PostMapping("/courses/register")
    public ResponseEntity<CourseShortDetailsResponseDto> registerCourse(@RequestBody CourseRegisterRequestDto requestDto) {
        log.info("Start registering course with request: {}", requestDto);
        final var responseDto = courseService.register(requestDto);
        final var result = ResponseEntity.status(responseDto.getStatus()).body(responseDto);
        log.info("Finished registering course with request: {}", requestDto);
        return result;
    }
}
