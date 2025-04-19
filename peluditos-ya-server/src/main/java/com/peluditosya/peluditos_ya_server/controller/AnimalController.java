package com.peluditosya.peluditos_ya_server.controller;

import com.peluditosya.peluditos_ya_server.dto.AnimalRegistrationRequest;
import com.peluditosya.peluditos_ya_server.model.Animal;
import com.peluditosya.peluditos_ya_server.service.AnimalService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.MediaType;

@RestController
@RequestMapping("/api/animals")
@CrossOrigin(origins = "http://localhost:4200")
public class AnimalController {
    private final AnimalService animalService;

    public AnimalController(AnimalService animalService) {
        this.animalService = animalService;
    }

    @PostMapping(value = "/register", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> registerAnimal(@ModelAttribute AnimalRegistrationRequest request) {
        try {
            Animal animal = animalService.registerAnimal(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(animal);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al registrar el animal");
        }
    }
}
