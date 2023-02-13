package com.example.lms.service;

import com.example.lms.dto.course.CourseDetailResponseDto;
import com.example.lms.dto.CourseRegisterRequestDto;
import com.example.lms.dto.CourseShortDetailsResponseDto;

import java.util.List;

public interface CourseService {
    CourseShortDetailsResponseDto register(CourseRegisterRequestDto course);

    List<CourseShortDetailsResponseDto> getAll();

    CourseDetailResponseDto getByUuid(String uuid);
}
