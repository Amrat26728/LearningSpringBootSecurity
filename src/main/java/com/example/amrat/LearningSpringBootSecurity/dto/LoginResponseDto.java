package com.example.amrat.LearningSpringBootSecurity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginResponseDto {

    private String jwt;
    private Long userId;

}
