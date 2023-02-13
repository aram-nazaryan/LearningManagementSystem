package com.example.lms.service;

import com.example.lms.domain.User;
import com.example.lms.dto.UserLoginRegistrationResponseDto;
import com.example.lms.dto.UserRegistrationRequestDto;

public interface UserMapper {

    User mapToUser(final UserRegistrationRequestDto requestDto);

    UserLoginRegistrationResponseDto mapToResponse(final User user);
}
