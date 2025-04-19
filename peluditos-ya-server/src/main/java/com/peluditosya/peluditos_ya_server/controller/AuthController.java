package com.peluditosya.peluditos_ya_server.controller;

import com.peluditosya.peluditos_ya_server.dto.AdopterSignUpRequest;
import com.peluditosya.peluditos_ya_server.dto.LoginRequest;
import com.peluditosya.peluditos_ya_server.dto.ShelterSignUpRequest;
import com.peluditosya.peluditos_ya_server.service.AuthService;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:4200")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/signup-adopter")
    public ResponseEntity<String> signupAdopter(@RequestBody AdopterSignUpRequest request) {
        String responseMessage = authService.signupAdopter(request);
        return ResponseEntity.ok(responseMessage);
    }

    @PostMapping(value = "/signup-shelter", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> signupShelter(@ModelAttribute ShelterSignUpRequest request) {
        String responseMessage = authService.signupShelter(request);
        return ResponseEntity.ok(responseMessage);
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginAdopter(@RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }
}
