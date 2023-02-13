package com.example.lms.dto;

import com.example.lms.domain.Role;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class UserRegistrationRequestDto {
    @JsonProperty("name")
    private String firstName;
    @JsonProperty("surname")
    private String lastName;
    @JsonProperty("email")
    private String email;
    @JsonProperty("phone")
    private String phoneNumber;
    @JsonProperty("password")
    private String password;
    @JsonProperty("role")
    private Role role;
}
