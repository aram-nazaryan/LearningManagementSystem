package com.example.lms.dto.course;

import com.example.lms.dto.AbstractErrorAwareResponse;
import com.example.lms.dto.ErrorDto;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@JsonInclude
public class CourseDetailResponseDto extends AbstractErrorAwareResponse {

    @JsonProperty("uuid")
    private String uuid;

    @JsonProperty("name")
    private String name;

    @JsonProperty("numberOfSessions")
    private Integer numberOfSessions;

    @JsonProperty("startDate")
    private LocalDate startDate;

    @JsonProperty("endDate")
    private LocalDate endDate;

    @JsonProperty("sessions")
    private List<SessionDto> sessions;

    private String trainerFirstName;
    private String trainerLastName;
    private String trainerUuid;

    public CourseDetailResponseDto(ErrorDto errorDto) {
        super(errorDto);
    }
}
