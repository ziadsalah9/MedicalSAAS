package com.MidecalApp.VideoConsultatntAppDemo.appointment.Entity;

import com.MidecalApp.VideoConsultatntAppDemo.doctor.entity.Doctor;
import com.MidecalApp.VideoConsultatntAppDemo.doctor.entity.DoctorAvailability;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TimeSlot {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "doctor_id")
    private Doctor doctor;

    private LocalDateTime startDateTime;

    private LocalDateTime endDateTime;

    private boolean isBooked=false;  // هل الموعد ده اتحجز خلاص؟

    @ManyToOne
    @JoinColumn(name = "availability_id")
    private DoctorAvailability availability;


}
