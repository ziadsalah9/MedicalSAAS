package com.MidecalApp.VideoConsultatntAppDemo.appointment.service;

import com.MidecalApp.VideoConsultatntAppDemo.appointment.Entity.TimeSlot;
import com.MidecalApp.VideoConsultatntAppDemo.appointment.repository.timeSlotRepository;
import com.MidecalApp.VideoConsultatntAppDemo.doctor.entity.Doctor;
import com.MidecalApp.VideoConsultatntAppDemo.doctor.entity.DoctorAvailability;
import com.MidecalApp.VideoConsultatntAppDemo.doctor.repository.doctorAvailabilityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class slotService {

    private final timeSlotRepository _timeSlotRepository;
    private final doctorAvailabilityRepository _doctorAvailabilityRepository;

    public String generateSlotDate(long doctorId, LocalDate date)
    {
        DayOfWeek day = date.getDayOfWeek();

        return _doctorAvailabilityRepository.findByDoctorIdAndDayOfWeek(doctorId, day)
                .map(availability -> {
                    generateSlot(availability, date); // الميثود القديمة اللي بتقطع الوقت
                    return "تم توليد المواعيد ليوم " + date + " (" + day + ")";
                })
                .orElseThrow(() -> new RuntimeException("الدكتور ليس لديه جدول مسجل ليوم " + day));
    }


    private void generateSlot (DoctorAvailability availability , LocalDate date){


    //1- time will doctor start
    var current = availability.getStartTime();
 //2- time of minute will doctor take with one patient
        var takenTime = availability.getSlotDurationMinutes();

        //3- time of doctor end his shift
        var endTimeShift = availability.getEndTime();

    while ((current.plusMinutes(takenTime).isBefore(endTimeShift))  // 10:30 +30 =11 ... 11< 12(Time of endShift)-->TRUE
    || current.plusMinutes(takenTime).equals(endTimeShift)    // 11:30 +30 =12  ... 12==12(Time of EndShift) //true
    )   //else  12 + 30 = 12:30 ... 12:30 >12 (time of end shift ) false

    {
        LocalDateTime startSlot = LocalDateTime.of(date.getYear(), date.getMonth(), date.getDayOfMonth(), current.getHour(), current.getMinute(), current.getSecond());

        LocalDateTime endSlot = startSlot.plusMinutes(takenTime);

        TimeSlot timeSlot = TimeSlot.builder()
                .doctor(availability.getDoctor())
                .startDateTime(startSlot)
                .endDateTime(endSlot)
                .isBooked(false)
                .availability(availability)
                .build();

        _timeSlotRepository.save(timeSlot);
        current = current.plusMinutes(availability.getSlotDurationMinutes());
    }
    }




    //search all isBooked for specific doctor

    public List<TimeSlot>  findAvailableSlotsForDoctor(long doctorId, LocalDate date)
    {

        LocalDateTime startDay = date.atStartOfDay();
        LocalDateTime endDay = date.atTime(LocalTime.MAX);

        System.out.println("startDay: " + startDay +"-----" +  " endDay: " + endDay);

        return _timeSlotRepository.findAvailableSlot(doctorId, startDay, endDay);

    }










    public TimeSlot findSlotById(long slotId) {

        var result = _timeSlotRepository.findById(slotId).orElseThrow(()->
                new RuntimeException(" this time is not exists with id "+slotId));
        return result;


    }

    public void save(TimeSlot timeSlot) {

        _timeSlotRepository.save(timeSlot);
    }



}
