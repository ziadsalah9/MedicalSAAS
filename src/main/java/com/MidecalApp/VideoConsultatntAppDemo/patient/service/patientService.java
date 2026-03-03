package com.MidecalApp.VideoConsultatntAppDemo.patient.service;

import com.MidecalApp.VideoConsultatntAppDemo.patient.Dto.patientResponseDto;
import com.MidecalApp.VideoConsultatntAppDemo.patient.Dto.patientRequestDto;
import com.MidecalApp.VideoConsultatntAppDemo.patient.entity.Patient;
import com.MidecalApp.VideoConsultatntAppDemo.patient.repository.patientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class patientService {

    private final patientRepository _patientRepository;

    public Patient findPatientById(Long id) {

        return _patientRepository.findById(id).get();
    }


    public long addPatient (patientRequestDto patientDto)

    {

        var patient = Patient.builder()

                .fullName(patientDto.fullName())
                .email(patientDto.email())
                .phone(patientDto.phone())
                .dateOfBirth(patientDto.dateOfBirth())
                .gender(patientDto.gender())
                .addressLine(patientDto.addressLine())
                .city(patientDto.city())
                .country(patientDto.country())
                .WalletBalance(patientDto.WalletBalance())

                .createdAt(LocalDateTime.now())
                .build();

        _patientRepository.save(patient);

        return patient.getId();
    }



    public List<Patient>  findAllPatients()
    {
        return _patientRepository.findAll();
    }


}
