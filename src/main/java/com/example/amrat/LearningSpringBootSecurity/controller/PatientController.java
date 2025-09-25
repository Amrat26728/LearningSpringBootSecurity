package com.example.amrat.LearningSpringBootSecurity.controller;

import com.example.amrat.LearningSpringBootSecurity.dto.AppointmentResponseDto;
import com.example.amrat.LearningSpringBootSecurity.dto.CreateAppointmentRequestDto;
import com.example.amrat.LearningSpringBootSecurity.dto.PatientResponseDto;
import com.example.amrat.LearningSpringBootSecurity.service.AppointmentService;
import com.example.amrat.LearningSpringBootSecurity.service.PatientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/patients")
@RequiredArgsConstructor
public class PatientController {

    private final PatientService patientService;
    private final AppointmentService appointmentService;

    @PostMapping("/appointments")
    public ResponseEntity<AppointmentResponseDto> createNewAppointment(@RequestBody CreateAppointmentRequestDto createAppointmentRequestDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(appointmentService.createNewAppointment(createAppointmentRequestDto));
    }

    @GetMapping("/profile")
    private ResponseEntity<PatientResponseDto> getPatientProfile() {
        Long patientId = 4L;
        return ResponseEntity.ok(patientService.getPatientById(patientId));
    }

}
