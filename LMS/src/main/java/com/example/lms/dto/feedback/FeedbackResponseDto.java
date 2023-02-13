package com.example.lms.dto.feedback;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FeedbackResponseDto {

    private String userUuid;

    private String userFirstName;

    private String userLastName;

    private String userEmail;

    private String attendancePercentage;

    private List<CourseFeedbackDto> courseFeedbackDtos;
}
