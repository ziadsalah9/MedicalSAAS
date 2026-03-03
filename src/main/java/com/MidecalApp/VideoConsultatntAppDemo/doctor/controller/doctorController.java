package com.MidecalApp.VideoConsultatntAppDemo.doctor.controller;

import com.MidecalApp.VideoConsultatntAppDemo.doctor.dto.doctorRequestDto;
import com.MidecalApp.VideoConsultatntAppDemo.doctor.entity.Doctor;
import com.MidecalApp.VideoConsultatntAppDemo.doctor.service.doctorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/doctors")
@RequiredArgsConstructor
public class doctorController {

    private final doctorService service;


    @PostMapping
    public ResponseEntity<?> addDoctor(@RequestBody doctorRequestDto doctor){

        service.addDoctor(doctor);
        return ResponseEntity.ok("doctor added successfully");



    }

    @GetMapping("getAllDoctors")
    public ResponseEntity<?> getAllDoctors(){
        return ResponseEntity.ok(service.getAllDoctors());
    }

    @GetMapping("{id}/doctor")
    public ResponseEntity<?> getDoctorById(@PathVariable Long id){
        return ResponseEntity.ok(service.getDoctorById(id));
    }

    @GetMapping("name/{name}/doctor")
    public ResponseEntity<?> getDoctorByName(@PathVariable String name){

        var result = service.GetDoctorByNAME(name);

        if (result!=null){
            return ResponseEntity.ok(result);
        }
        else return ResponseEntity.notFound().build();
    }


}
