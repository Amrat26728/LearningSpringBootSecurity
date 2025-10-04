package com.example.amrat.LearningSpringBootSecurity.service;

import com.example.amrat.LearningSpringBootSecurity.dto.DoctorResponseDto;
import com.example.amrat.LearningSpringBootSecurity.dto.OnboardDoctorRequestDto;
import com.example.amrat.LearningSpringBootSecurity.entity.Doctor;
import com.example.amrat.LearningSpringBootSecurity.entity.User;
import com.example.amrat.LearningSpringBootSecurity.entity.type.RoleType;
import com.example.amrat.LearningSpringBootSecurity.repository.DoctorRepository;
import com.example.amrat.LearningSpringBootSecurity.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class DoctorService {

    private final DoctorRepository doctorRepository;
    private final ModelMapper modelMapper;
    private final UserRepository userRepository;

    public List<DoctorResponseDto> getAllDoctors() {
        return doctorRepository.findAll()
                .stream()
                .map(doctor -> modelMapper.map(doctor, DoctorResponseDto.class))
                .collect(Collectors.toList());
    }


    @Transactional
    public DoctorResponseDto onBoardNewDoctor(OnboardDoctorRequestDto onboardDoctorRequestDto) {
        User user = userRepository.findById(onboardDoctorRequestDto.getUserId()).orElseThrow(() -> new IllegalArgumentException("User not found."));

        if (doctorRepository.existsById(onboardDoctorRequestDto.getUserId())) {
            throw new IllegalArgumentException("Already a doctor.");
        }

        Doctor doctor = Doctor.builder()
                .name(onboardDoctorRequestDto.getName())
                .specialization(onboardDoctorRequestDto.getSpecialization())
                .user(user)
                .build();

        doctorRepository.save(doctor);
        user.getRoles().add(RoleType.DOCTOR);
        return modelMapper.map(doctor, DoctorResponseDto.class);
    }
}
