package com.example.lms.dto;

import com.example.lms.domain.Role;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDetailsResponseDto  extends AbstractErrorAwareResponse {

    private String uuid;

    private String firstName;

    private String lastName;

    private String email;

    private String phoneNumber;

    private LocalDateTime registeredAt;

    private LocalDateTime modifiedAt;

    private Role role;

    private List<CourseShortDetailsResponseDto> courses;

    public UserDetailsResponseDto(ErrorDto errorDto) {
        super(errorDto);
    }

}
