package com.example.amrat.LearningSpringBootSecurity.controller;

import com.example.amrat.LearningSpringBootSecurity.dto.LoginAndSignupRequestDto;
import com.example.amrat.LearningSpringBootSecurity.dto.LoginResponseDto;
import com.example.amrat.LearningSpringBootSecurity.dto.SignupResponseDto;
import com.example.amrat.LearningSpringBootSecurity.security.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody LoginAndSignupRequestDto loginRequestDto){
        return ResponseEntity.ok(authService.login(loginRequestDto));
    }

    @PostMapping("/signup")
    public ResponseEntity<SignupResponseDto> signup(@RequestBody LoginAndSignupRequestDto signupRequestDto){
        return ResponseEntity.ok(authService.signup(signupRequestDto));
    }

}
