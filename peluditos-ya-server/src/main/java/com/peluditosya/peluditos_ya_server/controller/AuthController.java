package com.peluditosya.peluditos_ya_server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.peluditosya.peluditos_ya_server.dto.LoginRequest;
import com.peluditosya.peluditos_ya_server.dto.SignUpRequest;
import com.peluditosya.peluditos_ya_server.dto.LoginResponse;
import com.peluditosya.peluditos_ya_server.model.Adopter;
import com.peluditosya.peluditos_ya_server.model.AppUser;
import com.peluditosya.peluditos_ya_server.repository.UserRepository;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:4200")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody SignUpRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            return ResponseEntity.badRequest().body("El correo ya está en uso");
        }

        Adopter adopter = new Adopter();
        adopter.setName(request.getName());
        adopter.setEmail(request.getEmail());
        adopter.setPassword(request.getPassword());
        adopter.setLocation(request.getLocation());
        adopter.setPhone(request.getPhone());
        adopter.setAdmin(false); // Se registra como usuario normal por defecto

        userRepository.save(adopter);
        return ResponseEntity.ok("Adoptante registrado");
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        AppUser user = userRepository.findByEmailAndPassword(request.getEmail(), request.getPassword());

        if (user != null) {
            LoginResponse response = new LoginResponse("Login exitoso", user.isAdmin());
            return ResponseEntity.ok(response);
        }

        return ResponseEntity.status(401).body(new LoginResponse("Credenciales inválidas", false));
    }
}
