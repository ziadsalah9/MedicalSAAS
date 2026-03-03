package com.MidecalApp.VideoConsultatntAppDemo.doctor.service;

import com.MidecalApp.VideoConsultatntAppDemo.doctor.dto.doctorRequestDto;
import com.MidecalApp.VideoConsultatntAppDemo.doctor.dto.doctorResponeDto;
import com.MidecalApp.VideoConsultatntAppDemo.doctor.entity.Doctor;
import com.MidecalApp.VideoConsultatntAppDemo.doctor.repository.doctorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class doctorService {



    private final doctorRepository _doctorRepository;

    public long addDoctor(doctorRequestDto doctordto){

        var result = Doctor.builder()
                .fullName(doctordto.fullName())
                .phone(doctordto.phone())
                .email(doctordto.email())
                .clinicAddress(doctordto.clinicAddress())
                .specialization( doctordto.specialization() )
                .ratePerMinute(doctordto.ratePerMinute())
                .createdAt(LocalDateTime.now())

                .build();

        _doctorRepository.save(result);
        return result.getId();
    }


    public doctorResponeDto getDoctorById(Long id){

       var doctor =   _doctorRepository.getById(id);

       doctorResponeDto result = doctorResponeDto.builder()
               .id(doctor.getId())
               .fullName(doctor.getFullName())
               .phone(doctor.getPhone())
               .email(doctor.getEmail())
               .clinicAddress(doctor.getClinicAddress())
               .specialization(doctor.getSpecialization())
               .ratePerMinute(doctor.getRatePerMinute())
               .createdAt(doctor.getCreatedAt())
               .build();

       return result;

    }

    public List<doctorResponeDto> getAllDoctors(){

        var doctors= _doctorRepository.findAll();

        List<doctorResponeDto> doctorsList = new ArrayList<>();
        for (var docs : doctors){

            var  result = doctorResponeDto.builder()
                    .id(docs.getId())
                    .fullName(docs.getFullName())
                    .phone(docs.getPhone())
                    .email(docs.getEmail())
                    .clinicAddress(docs.getClinicAddress())
                    .specialization(docs.getSpecialization())
                    .ratePerMinute(docs.getRatePerMinute())
                    .createdAt(docs.getCreatedAt())
                    .build();

            doctorsList.add(result);

        }

        return doctorsList;



    }


    public doctorResponeDto GetDoctorByNAME(String Name){

       var result =  _doctorRepository.getDoctorByFullName(Name);
        if(result!=null){
            return result;
        }
        else return   null;

    }


    public Doctor getDoctorEntityById(long doctorId) {

        return _doctorRepository.findById(doctorId)
                .orElseThrow(() -> new RuntimeException("Doctor not found"));
    }
}
