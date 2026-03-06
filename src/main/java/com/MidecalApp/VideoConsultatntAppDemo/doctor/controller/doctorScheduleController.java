package com.MidecalApp.VideoConsultatntAppDemo.doctor.controller;

import com.MidecalApp.VideoConsultatntAppDemo.doctor.entity.DoctorAvailability;
import com.MidecalApp.VideoConsultatntAppDemo.doctor.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.MidecalApp.VideoConsultatntAppDemo.appointment.service.slotService;
    import com.MidecalApp.VideoConsultatntAppDemo.doctor.dto.*;
import java.time.LocalDate;

@RestController
@RequestMapping("/api/doctor/schedule")
//@RequestMapping("/api/doctor/")
@RequiredArgsConstructor
public class doctorScheduleController {

    private final slotService _slotService;

    private final doctorService _doctorService;

    @PostMapping("createDoctorAvailability")
    public ResponseEntity createDoctorAvailability(@RequestBody doctorAvailbilityRequestDto doctorAvailability){

        _doctorService.SetDoctorAvailability(doctorAvailability);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/getAllAvailabilityWithDoctorId/{DoctorId}")
    public ResponseEntity<?> getAllDoctorAvailability(@PathVariable long DoctorId){

       var result =  _doctorService.GetAllAvailbailtiesforDoctor(DoctorId);
        if (result!=null){

            return ResponseEntity.ok().body(result);
        }
        return ResponseEntity.notFound().build();

    }

    @PostMapping("/generate")
    public ResponseEntity<String> createWeeklySchedule(@RequestParam long doctorId, @RequestParam LocalDate date)
    {
        _slotService.generateSlotDate(doctorId, date);
        return ResponseEntity.ok("Slots generated successfully for " + date);
    }


}

