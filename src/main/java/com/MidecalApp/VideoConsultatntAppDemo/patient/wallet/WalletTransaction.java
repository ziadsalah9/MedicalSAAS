package com.MidecalApp.VideoConsultatntAppDemo.patient.wallet;

import com.MidecalApp.VideoConsultatntAppDemo.patient.entity.Patient;
import com.MidecalApp.VideoConsultatntAppDemo.patient.wallet.Enums.WalletTransactionType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class WalletTransaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "patient_id")
    private Patient patient;

    private Double amount;

    @Enumerated(EnumType.STRING)
    private WalletTransactionType type;
    // DEPOSIT
    // HOLD
    // CONSULTATION_PAYMENT
    // REFUND
    private String reference; // appointmentId مثلا

    private LocalDateTime createdAt;



}
