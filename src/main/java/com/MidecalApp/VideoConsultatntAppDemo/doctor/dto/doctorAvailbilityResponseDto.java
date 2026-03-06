package com.MidecalApp.VideoConsultatntAppDemo.doctor.dto;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Builder;

import java.time.DayOfWeek;
import java.time.LocalTime;

@Builder
public record doctorAvailbilityResponseDto(
        @Enumerated(EnumType.STRING)
        DayOfWeek dayOfWeek,


        LocalTime startTime,
        LocalTime endTime,

        int slotDurationMinutes
        ,
        doctorResponeDto doctorResponeDto

) {
}
