package com.MidecalApp.VideoConsultatntAppDemo.video.controllers;

import com.MidecalApp.VideoConsultatntAppDemo.video.service.videoCallWebhookService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/video/webhook")

public class TwilioWebhookController {


//    @PostMapping
//
//    public ResponseEntity<String> createWebhook(@RequestParam Map<String,String> body) {
//
//        System.out.println("webhook Received :  " + body);
//        return ResponseEntity.ok("webhook Received");
//    }



    private final videoCallWebhookService _videoCallWebhookService;

    TwilioWebhookController(videoCallWebhookService videoCallWebhookService) {
        _videoCallWebhookService = videoCallWebhookService;
    }
    @PostMapping("/{doctorId}")

    public ResponseEntity<String> createWebhook(@PathVariable long doctorId,@RequestParam Map<String,String> body) {

       _videoCallWebhookService.handleTwilioEvent(doctorId,body);


        body.forEach((k,v) -> System.out.println(k + " = " + v));

        return ResponseEntity.ok("webhook Received");
    }
}
