package com.peluditosya.peluditos_ya_server.controller;

import com.peluditosya.peluditos_ya_server.dto.AdopterSignUpRequest;
import com.peluditosya.peluditos_ya_server.dto.LoginRequest;
import com.peluditosya.peluditos_ya_server.dto.ShelterSignUpRequest;
import com.peluditosya.peluditos_ya_server.service.AuthService;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import com.peluditosya.peluditos_ya_server.service.MailService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
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

    @PostMapping(value = "/signup-shelter", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
        public ResponseEntity<String> signupShelter(@ModelAttribute ShelterSignUpRequest request) {
        try {
            String responseMessage = authService.signupShelter(request);
            mailService.sendWelcomeEmail(request.getEmail(), request.getShelterName(), "SHELTER");
            return ResponseEntity.ok(responseMessage);
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("El correo electrónico o nombre del refugio ya está registrado.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error en el servidor");
        }
}

    @PostMapping("/login")
    public ResponseEntity<?> loginAdopter(@RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }
}

