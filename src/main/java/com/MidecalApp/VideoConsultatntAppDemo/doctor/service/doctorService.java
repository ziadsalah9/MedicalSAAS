package com.MidecalApp.VideoConsultatntAppDemo.doctor.service;

import com.MidecalApp.VideoConsultatntAppDemo.doctor.dto.doctorAvailbilityRequestDto;
import com.MidecalApp.VideoConsultatntAppDemo.doctor.dto.doctorAvailbilityResponseDto;
import com.MidecalApp.VideoConsultatntAppDemo.doctor.dto.doctorRequestDto;
import com.MidecalApp.VideoConsultatntAppDemo.doctor.dto.doctorResponeDto;
import com.MidecalApp.VideoConsultatntAppDemo.doctor.entity.Doctor;
import com.MidecalApp.VideoConsultatntAppDemo.doctor.entity.DoctorAvailability;
import com.MidecalApp.VideoConsultatntAppDemo.doctor.repository.doctorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import com.MidecalApp.VideoConsultatntAppDemo.doctor.repository.doctorAvailabilityRepository;
@Service
@RequiredArgsConstructor
public class doctorService {



    private final doctorRepository _doctorRepository;
    private final doctorAvailabilityRepository _doctorAvailabilityRepository;

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
    
    public long SetDoctorAvailability(doctorAvailbilityRequestDto RequestDto) {
        
        
        var doctor = _doctorRepository.findById(RequestDto.doctorId()).orElseThrow();
        var doctorAvailability = DoctorAvailability.builder()
                .doctor(doctor)
                .startTime(RequestDto.startTime())
                .endTime(RequestDto.endTime())
                .dayOfWeek(RequestDto.dayOfWeek())
                .slotDurationMinutes(RequestDto.slotDurationMinutes())
                .build();
        _doctorAvailabilityRepository.save(doctorAvailability);
     return doctorAvailability.getId();   
    }


    public List<doctorAvailbilityResponseDto> GetAllAvailbailtiesforDoctor(long doctorId) {


        var doctor =  _doctorRepository.findById(doctorId).orElseThrow();

//        List<Long> ids = new ArrayList<>();
//        ids.add(doctor.getId());

        var doctorAvilabilty = _doctorAvailabilityRepository.findByDoctorId(doctorId);

        List<doctorAvailbilityResponseDto> responseDtoList = new ArrayList<>();
        for (var item :  doctorAvilabilty) {
            var doctorResponseDto = doctorResponeDto.builder()
                    .id(doctor.getId())
                    .clinicAddress(item.getDoctor().getClinicAddress())
                    .specialization(item.getDoctor().getSpecialization())
                    .ratePerMinute(item.getDoctor().getRatePerMinute())
                    .createdAt(item.getDoctor().getCreatedAt())
                    .email(item.getDoctor().getEmail())
                    .fullName(item.getDoctor().getFullName())
                    .phone(item.getDoctor().getPhone())
                    .build();
            doctorAvailbilityResponseDto dto = doctorAvailbilityResponseDto.builder()
                    .dayOfWeek(item.getDayOfWeek())
                    .startTime(item.getStartTime())
                    .endTime(item.getEndTime())
                    .slotDurationMinutes(item.getSlotDurationMinutes())
                    .doctorResponeDto(doctorResponseDto)
                .build();
            responseDtoList.add(dto);
        }

        return responseDtoList;
    }
}
