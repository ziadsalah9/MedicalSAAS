package com.MidecalApp.VideoConsultatntAppDemo.appointment.Dtos;

import com.MidecalApp.VideoConsultatntAppDemo.appointment.Enums.sessionStatus;
import com.MidecalApp.VideoConsultatntAppDemo.video.Enums.sessionType;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class appointmentResponseDto {

    private Long id;
    private Long patientId;
    private String patientName;
    private Long doctorId;
    private LocalDateTime scheduledAt;
    private sessionType type;
    private sessionStatus status;
    private Double price;
    private LocalDateTime createdAt;
}
