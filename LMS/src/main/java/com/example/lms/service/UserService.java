package com.example.lms.service;

import com.example.lms.domain.Role;
import com.example.lms.domain.User;
import com.example.lms.dto.*;

import java.util.List;

import java.util.Optional;


public interface UserService {
    UserLoginRegistrationResponseDto register(final UserRegistrationRequestDto requestDto);

    UserLoginRegistrationResponseDto login(final UserLoginRequestDto requestDto);

    Boolean isBodyInvalid(final UserRegistrationRequestDto requestDto);

    Boolean isUserRegistered(final UserRegistrationRequestDto requestDt);

    Optional<User> loginApproved(final UserLoginRequestDto requestDto);

    List<UserShortDetailsResponseDto> getAll();

    UserDetailsResponseDto getByUuid(String uuid);

    List<UserShortDetailsResponseDto> getSpecificUserGroup(Role role);
}
