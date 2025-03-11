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

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody SignUpRequest request) {
        // Crear usuario básico (sin encriptar contraseña)
        // AppUser user = new AppUser();
        Adopter adopter = new Adopter();
        adopter.setName(request.getName());
        adopter.setEmail(request.getEmail());
        adopter.setPassword(request.getPassword()); // Almacena en texto plano
        adopter.setLocation(request.getLocation());
        // adopter.setPhone("+591 12345678");
        adopter.setPhone(request.getPhone());
        
        userRepository.save(adopter);
        return ResponseEntity.ok("Adoptante registrado");
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest request) {
        // Buscar usuario por email y contraseña (texto plano)
        AppUser user = userRepository.findByEmailAndPassword(request.getEmail(), request.getPassword());
        if (user != null) return ResponseEntity.ok("Login exitoso");
        else return ResponseEntity.status(401).body("Credenciales inválidas");
        
    }
}
