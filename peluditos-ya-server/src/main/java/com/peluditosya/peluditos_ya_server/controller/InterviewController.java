package com.peluditosya.peluditos_ya_server.controller;

import com.peluditosya.peluditos_ya_server.dto.InterviewRequestDTO;
import com.peluditosya.peluditos_ya_server.service.InterviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Collections;

@RestController
@RequestMapping("/api/interviews")
@RequiredArgsConstructor
public class InterviewController {

    private final InterviewService interviewService;

    @PostMapping
    public ResponseEntity<java.util.Map<String, String>> requestInterview(@RequestBody InterviewRequestDTO requestDto) {
        interviewService.processInterviewRequest(requestDto);
        return ResponseEntity.ok(Collections.singletonMap("message", "Solicitud de entrevista enviada con éxito al refugio"));
    }
}
