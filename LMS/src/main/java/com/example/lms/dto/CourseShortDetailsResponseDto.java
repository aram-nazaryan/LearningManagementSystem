package com.example.lms.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CourseShortDetailsResponseDto extends AbstractErrorAwareResponse {

    @JsonProperty("uuid")
    private String uuid;

    @JsonProperty("name")
    private String name;


}
