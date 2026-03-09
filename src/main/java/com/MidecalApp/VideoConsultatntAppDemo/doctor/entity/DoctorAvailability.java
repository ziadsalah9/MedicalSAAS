package com.MidecalApp.VideoConsultatntAppDemo.doctor.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DoctorAvailability {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "doctor_id")
    private Doctor doctor;

    @Enumerated(EnumType.STRING)
    private DayOfWeek dayOfWeek;

//    private LocalDateTime startTime;
//    private LocalDateTime endTime;

    private LocalTime startTime;
    private LocalTime endTime;

    private int slotDurationMinutes; // مدة الكشف (مثلاً 30 دقيقة)
    private Double clinicPrice; // سعر الكشف في العيادة لليوم ده
}
