package com.example.lms.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor
@AllArgsConstructor
public class UserLoginRegistrationResponseDto extends AbstractErrorAwareResponse {

    private String jwt;

    public UserLoginRegistrationResponseDto(ErrorDto errorDto) {
        super(errorDto);
    }
}
