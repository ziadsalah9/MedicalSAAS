package com.MidecalApp.VideoConsultatntAppDemo.patient.Dto;

import com.MidecalApp.VideoConsultatntAppDemo.patient.Gender;

import java.time.LocalDate;

public record patientRequestDto(


         String fullName,
         String email,
         String phone,
         LocalDate dateOfBirth,
         Gender gender,
         String addressLine,
         String city,
         String country,
         Double WalletBalance

) {
}
