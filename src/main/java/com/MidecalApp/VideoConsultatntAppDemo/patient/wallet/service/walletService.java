package com.MidecalApp.VideoConsultatntAppDemo.patient.wallet.service;

import com.MidecalApp.VideoConsultatntAppDemo.appointment.Entity.Appointment;
import com.MidecalApp.VideoConsultatntAppDemo.appointment.repository.appointmentRepository;
import com.MidecalApp.VideoConsultatntAppDemo.patient.entity.Patient;
import com.MidecalApp.VideoConsultatntAppDemo.patient.service.patientService;
import com.MidecalApp.VideoConsultatntAppDemo.patient.wallet.Enums.WalletTransactionType;
import com.MidecalApp.VideoConsultatntAppDemo.patient.wallet.WalletTransaction;
import com.MidecalApp.VideoConsultatntAppDemo.patient.wallet.repository.walletTransactionRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.function.Supplier;

@Service
@RequiredArgsConstructor
public class walletService {

    private  final walletTransactionRepository _walletRepository;
    private  final patientService _patientService;
    private final appointmentRepository _appointmentRepository;


    @Transactional
    public void Deposit (long patientId , Double amount){

        var patient = _patientService.findPatientById(patientId);
        patient.setWalletBalance(patient.getWalletBalance()+amount);

        WalletTransaction walletTransaction = new WalletTransaction();
        walletTransaction.setPatient(patient);
        walletTransaction.setAmount(amount);
        walletTransaction.setType(WalletTransactionType.DEPOSIT);
        walletTransaction.setCreatedAt(LocalDateTime.now());
        _walletRepository.save(walletTransaction);

    }


    public WalletTransaction getWalletTransactionById(long id) {

      return   _walletRepository.findById(id).orElseThrow();
    }


    @Transactional
    public void holdAmount(Appointment appointment){
        Patient patient = appointment.getPatient();
        Double price = appointment.getPrice();

        if (patient.getWalletBalance()<price){

            throw new RuntimeException("Insufficient balance");

        }

        patient.setWalletBalance(patient.getWalletBalance()-price);

        //appointment.setHeldAmount(appointment.getHeldAmount()+price);
        appointment.setHeldAmount(price);
        _appointmentRepository.save(appointment); //
        WalletTransaction tx = new WalletTransaction();
        tx.setPatient(patient);
        tx.setAmount(price);
        tx.setType(WalletTransactionType.HOLD);
        tx.setReference("APPT_" + appointment.getId());
        tx.setCreatedAt(LocalDateTime.now());
        _walletRepository.save(tx);
    }


    @Transactional
    public void refundAmount(Appointment appointment){
        Patient patient = appointment.getPatient();
        Double held = appointment.getHeldAmount();
        var difference = held - appointment.getFinalAmount();

        //  سجل الدفع الفعلي
        WalletTransaction payment = new WalletTransaction();
        payment.setPatient(patient);
        payment.setAmount(appointment.getFinalAmount());
        payment.setType(WalletTransactionType.CONSULTATION_PAYMENT);
        payment.setReference("APPT_" + appointment.getId());
        payment.setCreatedAt(LocalDateTime.now());

        _walletRepository.save(payment);


        if (difference>0){
            patient.setWalletBalance(
                    patient.getWalletBalance() + difference
            );

            WalletTransaction refund = new WalletTransaction();
            refund.setPatient(patient);
            refund.setAmount(difference);
            refund.setType(WalletTransactionType.REFUND);
            refund.setReference("APPT_" + appointment.getId());
            refund.setCreatedAt(LocalDateTime.now());

            _walletRepository.save(refund);

            appointment.setHeldAmount(0.0);
            _appointmentRepository.save(appointment);

            System.out.println("✅ تم تسوية الحساب وتصفير المبلغ المحجوز للموعد: " + appointment.getId());
        

        }


    }

    private void createTransaction(Patient p, Double amt, WalletTransactionType type, Long apptId) {
        WalletTransaction tx = new WalletTransaction();
        tx.setPatient(p);
        tx.setAmount(amt);
        tx.setType(type);
        tx.setReference("APPT_" + apptId);
        tx.setCreatedAt(LocalDateTime.now());
        _walletRepository.save(tx);
    }
}
