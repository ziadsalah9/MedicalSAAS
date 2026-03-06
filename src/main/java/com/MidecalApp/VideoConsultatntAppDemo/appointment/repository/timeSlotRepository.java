package com.MidecalApp.VideoConsultatntAppDemo.appointment.repository;

import aj.org.objectweb.asm.commons.Remapper;
import com.MidecalApp.VideoConsultatntAppDemo.appointment.Entity.TimeSlot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.DayOfWeek;

@Repository
public interface timeSlotRepository extends JpaRepository<TimeSlot,Long> {
}
