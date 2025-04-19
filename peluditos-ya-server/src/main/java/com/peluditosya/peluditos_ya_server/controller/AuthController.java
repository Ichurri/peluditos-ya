package com.peluditosya.peluditos_ya_server.controller;

import com.peluditosya.peluditos_ya_server.dto.AdopterSignUpRequest;
import com.peluditosya.peluditos_ya_server.dto.LoginRequest;
import com.peluditosya.peluditos_ya_server.dto.ShelterSignUpRequest;
import com.peluditosya.peluditos_ya_server.service.AuthService;

import com.peluditosya.peluditos_ya_server.service.MailService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:4200")
public class AuthController {

    private final AuthService authService;
    private final MailService mailService;

    public AuthController(AuthService authService, MailService mailService) {
        this.authService = authService;
        this.mailService = mailService;
    }

    @PostMapping("/signup-adopter")
    public ResponseEntity<String> signupAdopter(@RequestBody AdopterSignUpRequest request) {
        String responseMessage = authService.signupAdopter(request);

        mailService.sendWelcomeEmail(request.getEmail(), request.getName(), "USER");

        return ResponseEntity.ok(responseMessage);
    }

    @PostMapping("/signup-shelter")
    public ResponseEntity<String> signupShelter(@RequestBody ShelterSignUpRequest request) {
        String responseMessage = authService.signupShelter(request);

        mailService.sendWelcomeEmail(request.getEmail(), request.getShelterName(), "SHELTER");

        return ResponseEntity.ok(responseMessage);
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginAdopter(@RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }
}

