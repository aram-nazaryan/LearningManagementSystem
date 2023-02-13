package com.example.lms.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class UserLoginRequestDto {
    @JsonProperty("username")
    private String username;

    @JsonProperty("password")
    private String password;
}
