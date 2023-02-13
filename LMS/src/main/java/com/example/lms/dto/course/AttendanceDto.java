package com.example.lms.dto.course;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude
public class AttendanceDto {
    private String uuid;
    private String firstName;
    private String lastName;
    private Boolean present;
    private Integer sessionNumber;
}
