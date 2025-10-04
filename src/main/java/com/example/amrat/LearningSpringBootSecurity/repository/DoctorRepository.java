package com.example.amrat.LearningSpringBootSecurity.repository;

import com.example.amrat.LearningSpringBootSecurity.dto.DoctorResponseDto;
import com.example.amrat.LearningSpringBootSecurity.dto.OnboardDoctorRequestDto;
import com.example.amrat.LearningSpringBootSecurity.entity.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {
}