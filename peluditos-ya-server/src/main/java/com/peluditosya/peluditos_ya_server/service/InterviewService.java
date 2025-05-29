package com.peluditosya.peluditos_ya_server.service;

import com.peluditosya.peluditos_ya_server.dto.InterviewRequestDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InterviewService {

    private final MailService mailService;

    public void processInterviewRequest(InterviewRequestDTO requestDto) {
        mailService.sendInterviewNotificationToShelter(
                requestDto.getShelterEmail(),
                requestDto.getUserName(),
                requestDto.getPetName(),
                requestDto.getInterviewDateTime()
        );
    }
}
