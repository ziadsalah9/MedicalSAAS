package com.MidecalApp.VideoConsultatntAppDemo.patient.repository;

import com.MidecalApp.VideoConsultatntAppDemo.patient.entity.Patient;
import com.MidecalApp.VideoConsultatntAppDemo.patient.Dto.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface patientRepository extends JpaRepository<Patient,Long> {

}
