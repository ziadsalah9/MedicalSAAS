package com.MidecalApp.VideoConsultatntAppDemo.doctor.dto;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

import java.time.DayOfWeek;
import java.time.LocalTime;

public record doctorAvailbilityRequestDto(

        Long doctorId,
        @Enumerated(EnumType.STRING)
         DayOfWeek dayOfWeek,


                 LocalTime startTime,
                 LocalTime endTime,

                 int slotDurationMinutes

        , Double clinicPrice// سعر الكشف في العيادة لليوم ده

)

{
}
