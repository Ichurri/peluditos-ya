package com.peluditosya.peluditos_ya_server.service;

import com.peluditosya.peluditos_ya_server.dto.AdopterSignUpRequest;
import com.peluditosya.peluditos_ya_server.dto.LoginRequest;
import com.peluditosya.peluditos_ya_server.dto.ShelterSignUpRequest;
import com.peluditosya.peluditos_ya_server.model.Adopter;
import com.peluditosya.peluditos_ya_server.model.AppUser;
import com.peluditosya.peluditos_ya_server.model.Role;
import com.peluditosya.peluditos_ya_server.model.Shelter;
import com.peluditosya.peluditos_ya_server.repository.UserRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final Logger logger = LoggerFactory.getLogger(AuthService.class);

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    private void validateEmailUniqueness(String email) {
        if (userRepository.existsByEmail(email)) {
            throw new IllegalArgumentException("El correo ya está en uso");
        }
    }

    public String signupAdopter(AdopterSignUpRequest request) {
        validateEmailUniqueness(request.getEmail());
        Adopter adopter = new Adopter();
        adopter.setName(request.getName());
        adopter.setEmail(request.getEmail());
        adopter.setPassword(passwordEncoder.encode(request.getPassword()));
        adopter.setCity(request.getCity());
        adopter.setPhone(request.getPhone());
        // El constructor de Adopter ya asigna el rol ADOPTER

        userRepository.save(adopter);
        logger.info("Nuevo adoptante registrado: {}", adopter.getEmail());
        return "Adoptante registrado";
    }

    public String signupShelter(ShelterSignUpRequest request) {
        validateEmailUniqueness(request.getEmail());

        Shelter shelter = new Shelter();
        shelter.setName(request.getName());
        shelter.setEmail(request.getEmail());
        shelter.setPassword(passwordEncoder.encode(request.getPassword()));
        shelter.setCity(request.getCity());
        shelter.setPhone(request.getPhone());
        shelter.setShelterName(request.getShelterName());
        shelter.setShelterAddress(request.getShelterAddress());

        // Handle PDF upload
        MultipartFile documentFile = request.getDocumentFile();
        if (documentFile != null && !documentFile.isEmpty()) {
            try {
                String uploadDir = System.getProperty("user.dir") + "/uploads/shelter-documents";
                Files.createDirectories(Paths.get(uploadDir));
                String fileName = System.currentTimeMillis() + "_" + documentFile.getOriginalFilename();
                Path filePath = Paths.get(uploadDir, fileName);
                documentFile.transferTo(filePath.toFile());
                shelter.setDocumentPath(filePath.toString());
            } catch (IOException e) {
                logger.error("Error al guardar el documento PDF", e);
                throw new RuntimeException("No se pudo guardar el documento PDF");
            }
        } else {
            shelter.setDocumentPath(null);
        }
        // El constructor de Shelter ya asigna el rol SHELTER

        userRepository.save(shelter);
        logger.info("Nuevo refugio registrado: {}", shelter.getEmail());
        return "Refugio registrado";
    }

    public Map<String, Object> login(LoginRequest request) {
        AppUser user = userRepository.findByEmail(request.getEmail());
        if (user == null || !passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new BadCredentialsException("Credenciales inválidas");
        }

        boolean isAdmin = user.getRole().equals(Role.ADMIN);

        Map<String, Object> response = new HashMap<>();
        response.put("message", "Login exitoso");
        response.put("role", user.getRole());
        response.put("admin", isAdmin);
        response.put("userId", user.getId());

        logger.info("Login exitoso para: {}", user.getEmail());
        return response;
    }
}