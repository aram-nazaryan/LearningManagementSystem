package com.example.lms.controller;

import com.example.lms.dto.UserLoginRegistrationResponseDto;
import com.example.lms.dto.UserLoginRequestDto;
import com.example.lms.dto.UserRegistrationRequestDto;
import com.example.lms.service.UserService;
import com.example.lms.util.JwtService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;
@CrossOrigin
@RestController
@Slf4j
@RequestMapping("/auth/user")
@RequiredArgsConstructor
public class AuthController {

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;
    private final AuthenticationManager authenticationManager;
    private final UserService userService;

    @PostMapping("/login")
    public ResponseEntity<UserLoginRegistrationResponseDto> login(@RequestBody UserLoginRequestDto requestDto) throws Exception {
        log.info("RegistrationController(POST) with request body - {}", requestDto);
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(requestDto.getUsername(), requestDto.getPassword())
            );
        } catch (BadCredentialsException e) {
            throw new Exception("Incorrect username or password", e);
        }

        final UserDetails userDetails = userDetailsService
                .loadUserByUsername(requestDto.getUsername());

        final String jwt = jwtService.generateToken(userDetails);

        return ResponseEntity.ok(new UserLoginRegistrationResponseDto(jwt));
    }

    @PostMapping("/register")
    public ResponseEntity<UserLoginRegistrationResponseDto> register(@RequestBody UserRegistrationRequestDto requestDto) {
        log.info("RegistrationController(POST) with body - {}", requestDto);
        UserLoginRegistrationResponseDto registeredUser = userService.register(requestDto);
        return ResponseEntity.status(registeredUser.getStatus()).body(registeredUser);
    }
}
