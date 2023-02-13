package com.example.lms.service;

import com.example.lms.domain.Task;
import com.example.lms.dto.TaskDetailsResponseDto;
import com.example.lms.dto.TaskDetailsRequestDto;
import com.example.lms.dto.course.update.CommentRequestDto;
import com.example.lms.dto.course.update.GradeRequestDto;

public interface TaskService {

    TaskDetailsResponseDto register(TaskDetailsRequestDto requestDto);

    Task getByUuid(String uuid);

    void updateGrade(GradeRequestDto requestDto);

    void updateComment(CommentRequestDto requestDto);
}
