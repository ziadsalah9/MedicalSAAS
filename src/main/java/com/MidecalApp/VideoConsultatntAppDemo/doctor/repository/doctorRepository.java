package com.MidecalApp.VideoConsultatntAppDemo.doctor.repository;

import com.MidecalApp.VideoConsultatntAppDemo.doctor.entity.Doctor;
import com.MidecalApp.VideoConsultatntAppDemo.doctor.dto.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface doctorRepository extends JpaRepository<Doctor,Long> {

    public doctorResponeDto getDoctorByFullName(String fullName);
}
