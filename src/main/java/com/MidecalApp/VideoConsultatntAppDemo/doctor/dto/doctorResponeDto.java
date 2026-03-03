package com.MidecalApp.VideoConsultatntAppDemo.doctor.dto;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record doctorResponeDto(


        long id,
        String fullName,
        String email,
        String phone,

        String specialization,
        String clinicAddress,
        Double ratePerMinute,
        LocalDateTime createdAt
) {
}
