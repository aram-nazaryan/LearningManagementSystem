package com.example.lms.controller;

import com.example.lms.dto.UserLoginRegistrationResponseDto;
import com.example.lms.dto.UserRegistrationRequestDto;
import com.example.lms.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")
@Slf4j
@RequiredArgsConstructor
public class RegistrationController {

    private final UserService userService;

    @CrossOrigin
    @PostMapping("/register")
    public ResponseEntity<UserLoginRegistrationResponseDto> register(@RequestBody UserRegistrationRequestDto requestDto) {
        log.info("RegistrationController(POST) with body - {}", requestDto);
        UserLoginRegistrationResponseDto registeredUser = userService.register(requestDto);
        return ResponseEntity.status(registeredUser.getStatus()).body(registeredUser);
    }

}
