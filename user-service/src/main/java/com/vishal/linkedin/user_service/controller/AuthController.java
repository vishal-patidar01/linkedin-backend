package com.vishal.linkedin.user_service.controller;

import com.vishal.linkedin.user_service.dto.LoggingRequestDto;
import com.vishal.linkedin.user_service.dto.SignupRequestDto;
import com.vishal.linkedin.user_service.dto.UserDto;
import com.vishal.linkedin.user_service.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<UserDto> signup(@RequestBody SignupRequestDto signupRequestDto) {
        UserDto userDto = authService.signUp(signupRequestDto);
        return new ResponseEntity<>(userDto, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoggingRequestDto loggingRequestDto) {
        String token = authService.login(loggingRequestDto);
        return new ResponseEntity<>(token, HttpStatus.CREATED);
    }
}
