package com.MidecalApp.VideoConsultatntAppDemo.doctor.repository;

import aj.org.objectweb.asm.commons.Remapper;
import com.MidecalApp.VideoConsultatntAppDemo.doctor.entity.DoctorAvailability;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.DayOfWeek;
import java.util.List;
import java.util.Optional;

@Repository
public interface doctorAvailabilityRepository  extends JpaRepository<DoctorAvailability, Long> {
    List<DoctorAvailability> findByDoctorId(Long doctorId);

   Optional< DoctorAvailability> findByDoctorIdAndDayOfWeek(long doctorId, DayOfWeek day);
}
