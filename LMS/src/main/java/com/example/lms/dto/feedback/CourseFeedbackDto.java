package com.example.lms.dto.feedback;

import lombok.Data;

import java.util.List;

@Data
public class CourseFeedbackDto {

    private String name;

    private List<TaskFeedbackDto> taskFeedbackDtos;

}
