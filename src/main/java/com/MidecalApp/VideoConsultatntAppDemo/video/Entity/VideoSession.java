package com.MidecalApp.VideoConsultatntAppDemo.video.Entity;


import com.MidecalApp.VideoConsultatntAppDemo.appointment.Entity.Appointment;
import com.MidecalApp.VideoConsultatntAppDemo.video.Enums.videoSessionStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "video_sessions")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class VideoSession {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    //private long appointmentId;
    @ManyToOne
    @JoinColumn(name = "appointment_id", nullable = false)
    private Appointment appointment;

    private String roomName;

    @Enumerated(EnumType.STRING)
    private videoSessionStatus status;


    private Long durationSeconds;

    public LocalDateTime createdAt;
    public LocalDateTime startedAt;
    public LocalDateTime endedAt;




}
