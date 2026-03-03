package com.MidecalApp.VideoConsultatntAppDemo.appointment.controllers;


import com.MidecalApp.VideoConsultatntAppDemo.appointment.Dtos.appointmentRequestDto;
import com.MidecalApp.VideoConsultatntAppDemo.appointment.service.appointmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/appointment")
@RequiredArgsConstructor
public class AppointmentController {

    private final appointmentService _appointmentService;

    @PostMapping
    public ResponseEntity<?> addAppointment(@RequestBody appointmentRequestDto appointment) {

       return ResponseEntity.ok( _appointmentService.CreateService(appointment));
    }


    @GetMapping("/{patientId}/getAppointmentsByPatientId")
    public ResponseEntity<?> getAppointmentByPatientId(@PathVariable long patientId){

        var result = _appointmentService.getAppointmentsById(patientId);

        if(result!=null){
            return ResponseEntity.ok(result);
        }
        return ResponseEntity.notFound().build();

    }

    @GetMapping("/{doctorId}/getAppointmentsByDoctorId")
    public ResponseEntity<?> getAppointmentByDoctorId(@PathVariable long doctorId){

        var result = _appointmentService.getAppointmentsByDoctorId(doctorId);

        if(result!=null){
            return ResponseEntity.ok(result);
        }
        return ResponseEntity.notFound().build();

    }
}
