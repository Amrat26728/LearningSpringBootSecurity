package com.example.amrat.LearningSpringBootSecurity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginAndSignupRequestDto {

    private String username;
    private String password;

}
