package com.example.amrat.LearningSpringBootSecurity.controller;

import com.example.amrat.LearningSpringBootSecurity.dto.DoctorResponseDto;
import com.example.amrat.LearningSpringBootSecurity.dto.OnboardDoctorRequestDto;
import com.example.amrat.LearningSpringBootSecurity.dto.PatientResponseDto;
import com.example.amrat.LearningSpringBootSecurity.service.DoctorService;
import com.example.amrat.LearningSpringBootSecurity.service.PatientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final PatientService patientService;
    private final DoctorService doctorService;

    @GetMapping("/patients")
    public ResponseEntity<List<PatientResponseDto>> getAllPatients(
            @RequestParam(value = "page", defaultValue = "0") Integer pageNumber,
            @RequestParam(value = "size", defaultValue = "10") Integer pageSize
    ) {
        return ResponseEntity.ok(patientService.getAllPatients(pageNumber, pageSize));
    }

    @PostMapping("/onBoardNewDoctor")
    public ResponseEntity<DoctorResponseDto> onBoardNewDoctor(@RequestBody OnboardDoctorRequestDto onboardDoctorDto){
        return ResponseEntity.status(HttpStatus.CREATED).body(doctorService.onBoardNewDoctor(onboardDoctorDto));
    }
}
