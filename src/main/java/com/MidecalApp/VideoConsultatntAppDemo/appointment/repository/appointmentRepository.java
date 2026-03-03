package com.MidecalApp.VideoConsultatntAppDemo.appointment.repository;

import com.MidecalApp.VideoConsultatntAppDemo.appointment.Entity.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface appointmentRepository  extends JpaRepository<Appointment,Long>  {

    public List<Appointment> findAllByPatientId(long patientId);
    public List<Appointment> findAllByDoctorId(long doctorId);


}
