package com.MidecalApp.VideoConsultatntAppDemo.appointment.service;

import com.MidecalApp.VideoConsultatntAppDemo.appointment.Dtos.*;
import com.MidecalApp.VideoConsultatntAppDemo.appointment.Entity.Appointment;
import com.MidecalApp.VideoConsultatntAppDemo.appointment.Enums.sessionStatus;
import com.MidecalApp.VideoConsultatntAppDemo.appointment.repository.appointmentRepository;
import com.MidecalApp.VideoConsultatntAppDemo.doctor.entity.Doctor;
import com.MidecalApp.VideoConsultatntAppDemo.doctor.service.doctorService;
import com.MidecalApp.VideoConsultatntAppDemo.patient.Dto.patientResponseDto;
import com.MidecalApp.VideoConsultatntAppDemo.doctor.dto.*;
import com.MidecalApp.VideoConsultatntAppDemo.patient.service.patientService;
import com.MidecalApp.VideoConsultatntAppDemo.video.Enums.sessionType;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import com.MidecalApp.VideoConsultatntAppDemo.appointment.service.*;
@Service
@RequiredArgsConstructor
public class appointmentService
{
    private final appointmentRepository _appointmentRepository;
   // private final patientRepository _patientRepository;
   // private final doctorRepository _doctorRepository;

    private final patientService _patientService;
    private final doctorService _doctorService;
    private final slotService _timeSlotService;
    public long CreateService (appointmentRequestDto dto){

//
//                if (dto.getType()!= sessionType.CLINIC){
//
//                    // logic of video appointment
//                }

     //   var patient = _patientRepository.findById(dto.getPatientId()).orElseThrow();

        var patient = _patientService.findPatientById(dto.getPatientId());
       // var doctor = _doctorService.getDoctorById(dto.getDoctorId());
        var doctor = _doctorService.getDoctorEntityById(dto.getDoctorId());
        var appointment = Appointment.builder()

                //.PatientId(dto.getPatientId())

                .patient(patient)
                .doctor( doctor)
                .scheduledAt(dto.getScheduledAt())
                .expectedDurationMinutes(dto.getExpectedDurationMinutes())
                .type(dto.getType())
                .price(dto.getPrice())
                .status(sessionStatus.SCHEDULED)
                .createdAt(LocalDateTime.now())
                .build();



       var result =  _appointmentRepository.save(appointment);





        return result.getId();

    }


    public List<appointmentResponseDto> getAppointmentsById(long patientId){
//
//       var patientdata = _patientService.findPatientById(patientId);
//         List<Appointment> list = _appointmentRepository.findAllByPatientId(patientdata.getId());
//
//
//         List<appointmentResponseDto> appointmentResponseDtoList = new ArrayList<>();
//
//        for (var item :list){
//        var appointmentDto =
//                appointmentResponseDto.builder()
//
//
//                //.patientId(result.getPatientId())
//                .patientId(item.getPatient().getId())
//                .patientName(item.getPatient().getFullName())
//                .doctorId(item.getDoctor().getId())
//                .scheduledAt(item.getScheduledAt())
//                .type(item.getType())
//                .status(item.getStatus())
//                .price(item.getPrice())
//                .createdAt(item.getCreatedAt())
//                .build();
//
//
//         appointmentResponseDtoList.add(appointmentDto);
//        }
//        return appointmentResponseDtoList;


        List<Appointment> list = _appointmentRepository.findAllByPatientId(patientId);

        // لو القائمة رجعت فاضية هنا، يبقى المشكلة في اسم الدالة في الـ Repository
        if (list.isEmpty()) {
            System.out.println("No appointments found for patient ID: " + patientId);
        }

        return list.stream()
                .map(item -> appointmentResponseDto.builder()
                        .patientId(item.getPatient().getId())
                        .patientName(item.getPatient().getFullName())
                        .doctorId(item.getDoctor().getId())
                        .scheduledAt(item.getScheduledAt())
                        .type(item.getType())
                        .status(item.getStatus())
                        .price(item.getPrice())
                        .createdAt(item.getCreatedAt())
                        .build())
                .toList();
    }

    public List<appointmentResponseDto> getAppointmentsByDoctorId(long doctorId){

        var doctorData = _doctorService.getDoctorById(doctorId);

     //   var result = _appointmentRepository.findById(doctorData.id()).orElseThrow();

        var result = _appointmentRepository.findAllByDoctorId(doctorData.id());

        List<appointmentResponseDto> finallist = new ArrayList<>();

        for (var docs : result) {
            var appointmentDto = appointmentResponseDto.builder()

                    //.patientId(result.getPatientId())
                    .patientId(docs.getPatient().getId())
                    .patientName(docs.getPatient().getFullName())
                    .doctorId(docs.getDoctor().getId())
                    .scheduledAt(docs.getScheduledAt())
                    .type(docs.getType())
                    .status(docs.getStatus())
                    .price(docs.getPrice())
                    .createdAt(docs.getCreatedAt())
                    .build();

            finallist.add(appointmentDto);
        }

        return finallist;
    }


    private Doctor maptoDoctor(doctorResponeDto dto){

        return   Doctor.builder()
                .fullName(dto.fullName())
                .email(dto.email())
                .phone(dto.phone())
                .specialization(dto.specialization())
                .ratePerMinute(dto.ratePerMinute())
                .clinicAddress(dto.clinicAddress())
                .createdAt(dto.createdAt())
                .build();
    }









    @Transactional
    public long bookSlot(long slotId, long patientId, sessionType type){

       var slot= _timeSlotService.findSlotById(slotId);
        var patient = _patientService.findPatientById(patientId);

        if(slot.isBooked()){

            throw new RuntimeException("some one booked this time. choose other slot!! ");
        }

        slot.setBooked(true);
        _timeSlotService.save(slot);

        double doctorRate = slot.getDoctor().getRatePerMinute();
        double estimatedPrice;

        var expectedTimeForSession = slot.getAvailability().getSlotDurationMinutes() ; // time of video that you expect
        var doctorPriceForCheckPatient=slot.getAvailability().getClinicPrice();
        if (type == sessionType.VIDEO) {
            estimatedPrice = doctorRate * expectedTimeForSession;
        } else {
            estimatedPrice = doctorPriceForCheckPatient;
        }

        var appointment = Appointment.builder()
                .patient(patient)
                .doctor(slot.getDoctor())
                .timeSlot(slot)
                .scheduledAt(slot.getStartDateTime())
                .status(sessionStatus.SCHEDULED)
                .type(type)
                .expectedDurationMinutes(expectedTimeForSession)
                .heldAmount(estimatedPrice)
                .createdAt(LocalDateTime.now())
                .build();


        var result = _appointmentRepository.save(appointment);

        return result.getId();
    }

}
