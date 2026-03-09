package com.MidecalApp.VideoConsultatntAppDemo.appointment.repository;

import aj.org.objectweb.asm.commons.Remapper;
import com.MidecalApp.VideoConsultatntAppDemo.appointment.Entity.TimeSlot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface timeSlotRepository extends JpaRepository<TimeSlot,Long> {




//    @Query("select t from TimeSlot t where t.doctor.id = :doctorId"+
//            "AND t.startDateTime>= :start"+"AND t.endDateTime<= :end" +
//            "AND t.isBooked=false"
//    )
@Query("SELECT t FROM TimeSlot t WHERE t.doctor.id = :doctorId " + // مسافة بعد doctorId
        "AND t.startDateTime >= :start " +                       // مسافة بعد start
        "AND t.endDateTime <= :end " +                           // مسافة بعد end
        "AND t.isBooked = false")
    List<TimeSlot> findAvailableSlot(


            @Param("doctorId")long doctorId,
            @Param("start")LocalDateTime start,
            @Param("end")LocalDateTime end

            );
}
