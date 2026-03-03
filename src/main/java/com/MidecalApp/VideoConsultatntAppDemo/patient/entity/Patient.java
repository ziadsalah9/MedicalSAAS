package com.MidecalApp.VideoConsultatntAppDemo.patient.entity;

import com.MidecalApp.VideoConsultatntAppDemo.patient.Gender;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name="Patient")

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private String fullName;
    private String email;
    private String phone;


    private LocalDate dateOfBirth;
    private Gender gender;

    private String addressLine;
    private String city;
    private String country;


    private Double WalletBalance =0.0;

    private LocalDateTime createdAt;
}
