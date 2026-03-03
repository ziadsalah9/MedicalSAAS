package com.MidecalApp.VideoConsultatntAppDemo.patient.Dto;

import com.MidecalApp.VideoConsultatntAppDemo.patient.Gender;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class patientResponseDto {



    private String fullName;
    private String email;
    private String phone;


    private LocalDate dateOfBirth;
    private Gender gender;

    private String addressLine;
    private String city;
    private String country;


    private Double WalletBalance;

    private LocalDateTime createdAt;

}
