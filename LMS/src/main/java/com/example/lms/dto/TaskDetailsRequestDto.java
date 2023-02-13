package com.example.lms.dto;

import com.example.lms.domain.TaskType;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TaskDetailsRequestDto {

    @JsonProperty("course_uuid")
    private String course_uuid;

    @JsonProperty("number")
    private Long number;

    @JsonProperty("taskType")
    private TaskType taskType;
}
