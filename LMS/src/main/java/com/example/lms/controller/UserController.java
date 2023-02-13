package com.example.lms.controller;

import com.example.lms.domain.Role;
import com.example.lms.dto.UserDetailsResponseDto;
import com.example.lms.dto.UserShortDetailsResponseDto;
import com.example.lms.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/dashboard")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @CrossOrigin
    @GetMapping("/users")
    public ResponseEntity<List<UserShortDetailsResponseDto>> getAllUsers() {
        log.info("Start getting all users");
        final var responseDto = userService.getAll();
        final var result = ResponseEntity.status(HttpStatus.OK).body(responseDto);
        log.info("Finished getting all users");
        return result;
    }

    @CrossOrigin
    @GetMapping("/users/group/{role}")
    public ResponseEntity<List<UserShortDetailsResponseDto>> getSpecifiedUserGroup(@PathVariable("role") String role) {
        log.info("Start getting all {}", role);
        Role userRole = (role.equals("student") ? Role.ROLE_STUDENT : role.equals("trainer") ? Role.ROLE_TRAINER : null);
        final var responseDto = userService.getSpecificUserGroup(userRole);
        final var result = ResponseEntity.status(responseDto.get(0).getStatus()).body(responseDto);
        log.info("Finished getting all {}", role);
        return result;
    }

    @CrossOrigin
    @GetMapping("/users/{uuid}")
    public ResponseEntity<UserDetailsResponseDto> getUserByUuid(@PathVariable("uuid") String uuid) {
        log.info("Start getting user by uuid: {}", uuid);
        final var responseDto = userService.getByUuid(uuid);
        final var result = ResponseEntity.status(responseDto.getStatus()).body(responseDto);
        log.info("Finished getting user by uuid: {}", uuid);
        return result;
    }
}
