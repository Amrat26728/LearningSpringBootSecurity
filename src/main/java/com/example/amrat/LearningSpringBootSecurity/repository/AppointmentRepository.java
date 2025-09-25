package com.example.amrat.LearningSpringBootSecurity.repository;

import com.example.amrat.LearningSpringBootSecurity.entity.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
}