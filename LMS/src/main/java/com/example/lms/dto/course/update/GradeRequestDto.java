package com.example.lms.dto.course.update;

import lombok.Data;

@Data
public class GradeRequestDto extends SessionInfoDto {
    private Double grade;
    private Boolean passed;
}
