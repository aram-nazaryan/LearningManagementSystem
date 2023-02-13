package com.example.lms.dto.course;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude
public class HomeworkDto {
    private String uuid;
    private String firstName;
    private String lastName;
    private Long number;
    private String type;
    private String comment;
    private Double grade;
    private Boolean passed;
}
