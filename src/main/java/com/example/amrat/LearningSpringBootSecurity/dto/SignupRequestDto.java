package com.example.amrat.LearningSpringBootSecurity.dto;

import com.example.amrat.LearningSpringBootSecurity.entity.type.RoleType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignupRequestDto {

    private String username;
    private String password;
    private String name;
    private Set<RoleType> roles = new HashSet<>();

}
