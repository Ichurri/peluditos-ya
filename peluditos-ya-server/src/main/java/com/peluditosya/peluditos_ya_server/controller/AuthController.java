package com.peluditosya.peluditos_ya_server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

import com.peluditosya.peluditos_ya_server.dto.LoginRequest;
import com.peluditosya.peluditos_ya_server.dto.Role;
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

        adopter.setRole(Role.ADOPTER); // Asegura que el rol no sea null

        userRepository.save(adopter);
        return ResponseEntity.ok("Adoptante registrado");
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

        shelter.setRole(Role.SHELTER); // Asegura que el rol no sea null

        userRepository.save(shelter);
        return ResponseEntity.ok("Refugio registrado");
    }

    @PostMapping("/login-adopter")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        try {
            AppUser user = this.userRepository.findByEmailAndPassword(request.getEmail(), request.getPassword());

            if (user == null) {
                return ResponseEntity.status(401).body(Map.of("message", "Credenciales inválidas"));
            }

            boolean isAdmin = user instanceof Shelter;

            return ResponseEntity.ok(Map.of(
                    "message", "Login exitoso",
                    "admin", isAdmin
            ));
        } catch (Exception e) {
            e.printStackTrace(); // Esto imprimirá el error en la consola de Spring Boot
            return ResponseEntity.status(500).body(Map.of("message", "Error en el servidor"));
        }
    }
}
