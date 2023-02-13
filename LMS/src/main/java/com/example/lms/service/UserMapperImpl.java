package com.example.lms.service;

import com.example.lms.domain.Role;
import com.example.lms.domain.User;
import com.example.lms.dto.UserLoginRegistrationResponseDto;
import com.example.lms.dto.UserRegistrationRequestDto;
import org.springframework.stereotype.Component;

@Component
public class UserMapperImpl implements UserMapper {
    @Override
    public User mapToUser(UserRegistrationRequestDto requestDto) {
        User user = new User();
        user.setFirstName(requestDto.getFirstName());
        user.setLastName(requestDto.getLastName());
        user.setRole(requestDto.getRole());
        user.setEmail(requestDto.getEmail());
        user.setPhoneNumber(requestDto.getPhoneNumber());
        user.setPassword(requestDto.getPassword());
        return user;
    }

    @Override
    public UserLoginRegistrationResponseDto mapToResponse(User user) {
        UserLoginRegistrationResponseDto responseDto = new UserLoginRegistrationResponseDto();
        responseDto.setStatus(200);
        return responseDto;
    }
}
