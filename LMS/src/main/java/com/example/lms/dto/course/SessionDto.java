package com.example.lms.dto.course;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
@JsonInclude
public class SessionDto {

    @JsonProperty("homeworks")
    private List<HomeworkDto> homeworks;

    @JsonProperty("attendance")
    private List<AttendanceDto> attendance;
}
