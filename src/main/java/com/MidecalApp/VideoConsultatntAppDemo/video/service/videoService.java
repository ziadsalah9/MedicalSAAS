package com.MidecalApp.VideoConsultatntAppDemo.video.service;

import com.MidecalApp.VideoConsultatntAppDemo.video.Entity.VideoSession;
import com.MidecalApp.VideoConsultatntAppDemo.video.Enums.videoSessionStatus;
import com.MidecalApp.VideoConsultatntAppDemo.appointment.repository.appointmentRepository;
import com.MidecalApp.VideoConsultatntAppDemo.video.repository.videoRepository;
import com.twilio.jwt.accesstoken.AccessToken;
import com.twilio.jwt.accesstoken.VideoGrant;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class videoService {


       @Value("${account-sid}")
        private String accountSid;
       @Value("${api-key}")
        private String apiKey;
       @Value("${api-secret}")
        private String apiSecret;




    private final videoRepository videoRepository;
    private final appointmentRepository appointmentRepository;
    public long CreateSession(long appointmentID){

        var appointment = appointmentRepository.findById(appointmentID)
                .orElseThrow();
        String roomName = "room_"+ UUID.randomUUID().toString();
        VideoSession videoSession = VideoSession.builder()
                .roomName(roomName)
                .appointment(appointment)
                .status(videoSessionStatus.CREATED)
                .createdAt(LocalDateTime.now())
                .build();

        videoRepository.save(videoSession);
        return videoSession.getId();

    }


    public String GenerateToken (long sessionId , String Identity){

        System.out.println("account sid : "+accountSid);
        System.out.println("api-key : "+apiKey);
        System.out.println("api-secret : "+apiSecret);

        var session= videoRepository.findById(sessionId).orElseThrow();

        VideoGrant grant = new VideoGrant();
        grant.setRoom(session.getRoomName());

        AccessToken accessToken = new AccessToken.Builder(accountSid,apiKey,apiSecret)
                .identity(Identity).grant(grant).build();

        return accessToken.toJwt();
    }


    public VideoSession getByRoomName (String roomName){
        var result =  videoRepository.findByRoomName(roomName).orElseThrow();
        System.out.println("result : "+result);
        return result;

    }
}
