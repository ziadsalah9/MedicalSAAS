package com.MidecalApp.VideoConsultatntAppDemo.doctor.dto;

public record doctorRequestDto(


        String fullName,
        String email,
        String phone,

        String specialization,
        String clinicAddress,
        Double ratePerMinute
) {
}
