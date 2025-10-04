package com.example.amrat.LearningSpringBootSecurity.dto;

import lombok.Data;

@Data
public class OnboardDoctorRequestDto {
    private Long userId;
    private String name;
    private String specialization;
}
