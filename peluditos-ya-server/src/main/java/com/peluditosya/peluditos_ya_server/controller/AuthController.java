package com.peluditosya.peluditos_ya_server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.peluditosya.peluditos_ya_server.dto.LoginRequest;
import com.peluditosya.peluditos_ya_server.dto.AdopterSignUpRequest;
import com.peluditosya.peluditos_ya_server.dto.ShelterSignUpRequest;
import com.peluditosya.peluditos_ya_server.model.Adopter;
import com.peluditosya.peluditos_ya_server.model.AppUser;
import com.peluditosya.peluditos_ya_server.model.Shelter;
import com.peluditosya.peluditos_ya_server.repository.UserRepository;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:4200")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/signup-adopter")
    public ResponseEntity<String> signup(@RequestBody AdopterSignUpRequest request) {
        if (userRepository.existsByEmail(request.getEmail()))
            return ResponseEntity.badRequest().body("El correo ya está en uso");

        Adopter adopter = new Adopter();
        adopter.setName(request.getName());
        adopter.setEmail(request.getEmail());
        adopter.setPassword(request.getPassword());
        adopter.setCity(request.getCity());
        adopter.setPhone(request.getPhone());

        userRepository.save(adopter);
        return ResponseEntity.ok("Adoptante registrado");
    }

    @PostMapping("/login-adopter")
    public ResponseEntity<String> login(@RequestBody LoginRequest request) {
        AppUser user = this.userRepository.findByEmailAndPassword(request.getEmail(), request.getPassword());
        return user != null
                ? ResponseEntity.ok("Login exitoso")
                : ResponseEntity.status(401).body("Credenciales inválidas");
    }

    @PostMapping("/signup-shelter")
    public ResponseEntity<String> signupShelter(@RequestBody ShelterSignUpRequest request) {
        if (userRepository.existsByEmail(request.getEmail()))
            return ResponseEntity.badRequest().body("El correo ya está en uso");

        Shelter shelter = new Shelter();
        shelter.setName(request.getName());
        shelter.setEmail(request.getEmail());
        shelter.setPassword(request.getPassword());
        shelter.setCity(request.getCity());
        shelter.setPhone(request.getPhone());
        shelter.setDocumentNumber(request.getDocumentNumber());
        shelter.setShelterName(request.getShelterName());
        shelter.setShelterAddress(request.getShelterAddress());

        userRepository.save(shelter);
        return ResponseEntity.ok("Refugio registrado");
    }

    @PostMapping("/login-shelter")
    public ResponseEntity<String> loginShelter(@RequestBody LoginRequest request) {
        AppUser user = userRepository.findByEmailAndPassword(request.getEmail(), request.getPassword());
        if (user == null)
            return ResponseEntity.status(401).body("Credenciales inválidas");

        if (!(user instanceof Shelter))
            return ResponseEntity.status(401).body("El usuario no es un refugio");

        return ResponseEntity.ok("Login exitoso");
    }
}
