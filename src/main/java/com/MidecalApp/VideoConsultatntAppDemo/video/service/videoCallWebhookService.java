package com.MidecalApp.VideoConsultatntAppDemo.video.service;

import com.MidecalApp.VideoConsultatntAppDemo.appointment.Entity.Appointment;
import com.MidecalApp.VideoConsultatntAppDemo.doctor.repository.doctorRepository;
import com.MidecalApp.VideoConsultatntAppDemo.doctor.service.doctorService;
import com.MidecalApp.VideoConsultatntAppDemo.patient.wallet.service.walletService;
import com.MidecalApp.VideoConsultatntAppDemo.video.Entity.VideoSession;
import com.MidecalApp.VideoConsultatntAppDemo.appointment.Enums.sessionStatus;
import com.MidecalApp.VideoConsultatntAppDemo.video.Enums.videoSessionStatus;
import com.MidecalApp.VideoConsultatntAppDemo.appointment.repository.appointmentRepository;
import com.MidecalApp.VideoConsultatntAppDemo.video.repository.videoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class videoCallWebhookService {


    private final videoService
            _videoService;

    private final appointmentRepository _appointmentRepository;
    private final videoRepository _videoRepository;
  //  private final doctorRepository _doctorRepository;
    private final doctorService _doctorService;
    private final walletService _walletService;



    @Transactional
    public void handleRoomEnded(Map<String,String> dataFromTwilio){

        String roomName = null;
        for (Map.Entry<String,String> entry : dataFromTwilio.entrySet()) {
            if(entry.getKey().trim().equals("RoomName")) {

                roomName = entry.getValue();


            }
        }

      //   roomName = dataFromTwilio.get("RoomName");
        Long Duration = Long.parseLong(dataFromTwilio.get("RoomDuration"));

        System.out.println("Room Ended "+roomName+" "+Duration);

        VideoSession session =_videoService.getByRoomName(roomName);

        Appointment appointment = session.getAppointment();


        appointment.setActualDurationSeconds(Duration);
        appointment.setStatus(sessionStatus.COMPLETED);

//        appointment.setPrice((Duration*Math.pow(60,-1))*10); // convert rom sec to min . the minute =10 EGP
//        // ex 120 sec = 2 minutes  = 20 EGP

        var doctorPricePerMinutes  = appointment.getDoctor().getRatePerMinute();

        var newprice=(Duration*Math.pow(60,-1))*doctorPricePerMinutes;
        appointment.setFinalAmount(newprice);

        _walletService.refundAmount(appointment);
        //2sec* price of doctor123 = 5 = 2*5 = 10
        _appointmentRepository.save(appointment);


        session.setStatus(videoSessionStatus.COMPLETED);
        session.setDurationSeconds(Duration);
        _videoRepository.save(session);


    }


    public void handleTwilioEvent(Map<String,String> data) {


      //  String event = data.get("StatusCallbackEvent");

//        data.keySet().forEach(k ->
//                System.out.println("KEY -> [" + k + "]"));

        String event = null;

        for (Map.Entry<String,String> entry : data.entrySet()) {
            if(entry.getKey().trim().equals("StatusCallbackEvent")) {
                event = entry.getValue();
                      data.keySet().forEach(k ->
               System.out.println("KEY -> [" + k + "]"));
                break;
            }
        }

        if("room-ended".equals(event)) {
            handleRoomEnded(data);
        }
    }


}
