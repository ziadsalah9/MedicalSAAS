package com.MidecalApp.VideoConsultatntAppDemo.appointment.Entity;

import com.MidecalApp.VideoConsultatntAppDemo.appointment.Enums.sessionStatus;
import com.MidecalApp.VideoConsultatntAppDemo.doctor.entity.Doctor;
import com.MidecalApp.VideoConsultatntAppDemo.patient.entity.Patient;
import com.MidecalApp.VideoConsultatntAppDemo.video.Enums.sessionType;
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
public class Appointment {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //private long PatientId;
  //  private long DoctorId;

    private LocalDateTime scheduledAt;

    private sessionType type; // Clinic , Video

    private sessionStatus status; //     // SCHEDULED, IN_PROGRESS, COMPLETED, CANCELLED, NO_SHOW

    private long expectedDurationMinutes;

    private long actualDurationSeconds;

    private Double price;
    private Double heldAmount;  // المبلغ المحجوز
    private Double finalAmount;

    private LocalDateTime   createdAt;


    @ManyToOne
    @JoinColumn(name = "patient_id")
    private Patient patient;

    @ManyToOne
    @JoinColumn(name = "doctor_id")
    private Doctor doctor;

}
