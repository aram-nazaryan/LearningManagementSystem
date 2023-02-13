package com.example.lms.service;

import com.example.lms.domain.Role;
import com.example.lms.domain.User;
import com.example.lms.dto.*;
import com.example.lms.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserLoginRegistrationResponseDto register(final UserRegistrationRequestDto requestDto) {
        log.info("UserServiceImpl(register) requestDto {}", requestDto);
        ErrorType errorType = null;
        if (isBodyInvalid(requestDto)) {
            errorType = ErrorType.INVALID_BODY;
            return new UserLoginRegistrationResponseDto(errorType.errorDto());
        }
        if (isUserRegistered(requestDto)) {
            errorType = ErrorType.USER_ALREADY_REGISTERED;
            return new UserLoginRegistrationResponseDto(errorType.errorDto());
        }
        User user = userMapper.mapToUser(requestDto);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        log.info("User successfully registered - {}", user);
        return userMapper.mapToResponse(user);
    }

    @Override
    public UserLoginRegistrationResponseDto login(UserLoginRequestDto requestDto) {
        Optional<User> foundUser = loginApproved(requestDto);
        if (foundUser.isPresent()) {
            User user = foundUser.get();
            return userMapper.mapToResponse(user);
        }
        ErrorType errorType = ErrorType.INCORRECT_LOGIN_DETAILS;
        return new UserLoginRegistrationResponseDto(errorType.errorDto());
    }

    @Override
    public Boolean isBodyInvalid(final UserRegistrationRequestDto requestDto) {
        log.info("Checking body Validity...");
        return requestDto.getEmail().isEmpty() ||
                requestDto.getFirstName().isEmpty() ||
                requestDto.getLastName().isEmpty() ||
                requestDto.getPhoneNumber().isEmpty();
    }

    @Override
    public Boolean isUserRegistered(final UserRegistrationRequestDto requestDto) {
        log.info("Checking isUserRegistered...");
        Optional<User> foundUser = userRepository.findUserByPhoneNumberOrEmail(requestDto.getPhoneNumber(), requestDto.getEmail());
        return foundUser.isPresent();
    }

    @Override
    public Optional<User> loginApproved(UserLoginRequestDto requestDto) {
        return userRepository.findUserByEmailAndPassword(requestDto.getUsername(), passwordEncoder.encode(requestDto.getPassword()));
    }

    @Override
    public List<UserShortDetailsResponseDto> getAll() {
        List<User> users = userRepository.findAll();
        List<UserShortDetailsResponseDto> responseDtos = new ArrayList<>();
        //modelMapper.map(users, responseDtos);
        for (User user : users) {
            responseDtos.add(modelMapper.map(user, UserShortDetailsResponseDto.class));
        }
        return responseDtos;
    }

    @Override
    public List<UserShortDetailsResponseDto> getSpecificUserGroup(Role role) {
        List<UserShortDetailsResponseDto> responseDtos = null;
        if (role == null) {
            ErrorType errorType = ErrorType.INCORRECT_PATH_VARIABLE;
            responseDtos = List.of(new UserShortDetailsResponseDto(errorType.errorDto()));
            return responseDtos;
        }
        List<User> trainers = userRepository.findUsersByRole(role);
        responseDtos = new ArrayList<>();
        //modelMapper.map(trainers, responseDtos);
        for (User trainer : trainers) {
            responseDtos.add(modelMapper.map(trainer, UserShortDetailsResponseDto.class));
        }
        return responseDtos;
    }

    @Override
    public UserDetailsResponseDto getByUuid(String uuid) {
        Optional<User> user = userRepository.findUserByUuid(uuid);
        if (user.isEmpty()) {
            return new UserDetailsResponseDto(Optional.of(ErrorType.USER_NOT_FOUND).get().errorDto());
        }
        UserDetailsResponseDto response = modelMapper.map(user, UserDetailsResponseDto.class);
        return response;
    }

}
