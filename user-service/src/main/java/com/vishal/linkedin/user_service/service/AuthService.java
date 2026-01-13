package com.vishal.linkedin.user_service.service;

import com.vishal.linkedin.user_service.dto.LoggingRequestDto;
import com.vishal.linkedin.user_service.dto.SignupRequestDto;
import com.vishal.linkedin.user_service.dto.UserDto;
import com.vishal.linkedin.user_service.entity.User;
import com.vishal.linkedin.user_service.exception.BadRequestException;
import com.vishal.linkedin.user_service.repository.UserRepository;
import com.vishal.linkedin.user_service.utils.PasswordUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.lang.module.ResolutionException;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final JwtService jwtService;

    public UserDto signUp(SignupRequestDto signupRequestDto) {

        boolean exists = userRepository.existsByEmail(signupRequestDto.getEmail());
        if(exists) {
            throw new BadRequestException("User already exists, cannot signup again.");
        }

        User user = modelMapper.map(signupRequestDto, User.class);
        user.setPassword(PasswordUtil.hashPassword(signupRequestDto.getPassword()));



        User savedUser = userRepository.save(user);
        return modelMapper.map(savedUser, UserDto.class);
    }

    public String login(LoggingRequestDto loggingRequestDto) {
        User user = userRepository.findByEmail(loggingRequestDto.getEmail())
                .orElseThrow(() -> new ResolutionException("User not found with email: "+loggingRequestDto.getEmail()));

        boolean isPasswordMatch = PasswordUtil.checkPassword(loggingRequestDto.getPassword(), user.getPassword());
        if(!isPasswordMatch) {
            throw new BadRequestException("Incorrect password");
        }

        return jwtService.generateAccessToken(user);
    }
}
