package com.MidecalApp.VideoConsultatntAppDemo.appointment.Dtos;

import com.MidecalApp.VideoConsultatntAppDemo.video.Enums.sessionType;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class appointmentRequestDto {

    private Long patientId;
    private Long doctorId;
    @JsonFormat(pattern = "yyyy-MM-d H:mm:ss")
    private LocalDateTime scheduledAt;
    private sessionType type; // VIDEO or CLINIC
    private Long expectedDurationMinutes;
    private Double price;
}
