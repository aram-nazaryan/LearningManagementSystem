package com.example.lms.dto.feedback;

import lombok.Data;

@Data
public class TaskFeedbackDto {

    private Long number;

    private String type;

    private String comment;

    private Double grade;

    private String passed;

}
