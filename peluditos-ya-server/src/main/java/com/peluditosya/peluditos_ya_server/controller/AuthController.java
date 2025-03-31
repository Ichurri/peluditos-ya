package com.peluditosya.peluditos_ya_server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.peluditosya.peluditos_ya_server.dto.LoginRequest;
import com.peluditosya.peluditos_ya_server.dto.SignUpRequest;
import com.peluditosya.peluditos_ya_server.model.Adopter;
import com.peluditosya.peluditos_ya_server.model.AppUser;
import com.peluditosya.peluditos_ya_server.repository.UserRepository;

import org.springframework.web.bind.annotation.CrossOrigin;


@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:4200")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody SignUpRequest request) {
        // Verificar si el usuario ya existe
        if (userRepository.existsByEmail(request.getEmail())) 
        {
            return ResponseEntity.badRequest().body("El correo ya está en uso");
        }

        // Crear el usuario
        Adopter adopter = new Adopter();
        adopter.setName(request.getName());
        adopter.setEmail(request.getEmail());
        adopter.setPassword(request.getPassword());
        adopter.setLocation(request.getLocation());
        adopter.setPhone(request.getPhone());

        // Guardar en la base de datos
        userRepository.save(adopter);

        return ResponseEntity.ok("Adoptante registrado");
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest request) {
        AppUser user = this.userRepository.findByEmailAndPassword(request.getEmail(), request.getPassword());
        return user != null ? ResponseEntity.ok("Login exitoso") : ResponseEntity.status(401).body("Credenciales inválidas");
    }
}