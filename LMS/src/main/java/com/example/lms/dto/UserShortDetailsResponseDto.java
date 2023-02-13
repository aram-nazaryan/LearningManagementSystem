package com.example.lms.dto;

import com.example.lms.domain.Role;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserShortDetailsResponseDto extends AbstractErrorAwareResponse {

    @JsonProperty("uuid")
    private String uuid;

    @JsonProperty("firstName")
    private String firstName;

    @JsonProperty("lastName")
    private String lastName;

    @JsonProperty("role")
    private Role role;

    public UserShortDetailsResponseDto(ErrorDto errorDto) {
        super(errorDto);
    }
}
