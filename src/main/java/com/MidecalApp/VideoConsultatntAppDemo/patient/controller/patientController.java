package com.MidecalApp.VideoConsultatntAppDemo.patient.controller;

import com.MidecalApp.VideoConsultatntAppDemo.patient.Dto.patientRequestDto;
import com.MidecalApp.VideoConsultatntAppDemo.patient.entity.Patient;
import com.MidecalApp.VideoConsultatntAppDemo.patient.service.patientService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/patients")
public class patientController {

    private final patientService service;
    public patientController(patientService patientService) {
        this.service = patientService;
    }

    @PostMapping
    public ResponseEntity<?> addPatient(@RequestBody patientRequestDto patient)
    {

        service.addPatient(patient);

        return ResponseEntity.ok().build();


    }

    @GetMapping("GetAll")
    public ResponseEntity<?> getAllPatient()
    {

        return ResponseEntity.ok(service.findAllPatients());
    }

}
