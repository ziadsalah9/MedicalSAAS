package com.MidecalApp.VideoConsultatntAppDemo.video.controllers;

import com.MidecalApp.VideoConsultatntAppDemo.video.service.videoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/video")
@RequiredArgsConstructor
public class VideoController {

    private final videoService service;

    @PostMapping("/sessions")

    public long create (@RequestParam long appointmentId)
    {
        return service.CreateSession(appointmentId);
    }

    @GetMapping("/sessions/{id}/token")
    public String token(@PathVariable long id,
                        @RequestParam String identity) {
        return service.GenerateToken(id, identity);
    }

}
