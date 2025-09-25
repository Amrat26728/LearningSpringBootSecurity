package com.example.amrat.LearningSpringBootSecurity.repository;

import com.example.amrat.LearningSpringBootSecurity.entity.Insurance;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InsuranceRepository extends JpaRepository<Insurance, Long> {
}