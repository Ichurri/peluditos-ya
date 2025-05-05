package com.peluditosya.peluditos_ya_server.controller;

import com.peluditosya.peluditos_ya_server.dto.AnimalRegistrationRequest;
import com.peluditosya.peluditos_ya_server.dto.AnimalResponse;
import com.peluditosya.peluditos_ya_server.model.Animal;
import com.peluditosya.peluditos_ya_server.repository.AnimalRepository;
import com.peluditosya.peluditos_ya_server.service.AnimalService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.MediaType;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/animals")
@CrossOrigin(origins = "http://localhost:4200")
public class AnimalController {

    private final AnimalService animalService;
    private final AnimalRepository animalRepository;

    public AnimalController(AnimalService animalService, AnimalRepository animalRepository) {
        this.animalService = animalService;
        this.animalRepository = animalRepository;
    }

    @PostMapping(value = "/register", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> registerAnimal(@ModelAttribute AnimalRegistrationRequest request) {
        try {
            Long shelterRequestId = request.getShelterId();
            Animal animal = animalService.registerAnimal(request, shelterRequestId);
            return ResponseEntity.status(HttpStatus.CREATED).body(animal);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al registrar el animal");
        }
    }

    @GetMapping
    public ResponseEntity<List<AnimalResponse>> obtenerTodasLasMascotas() {
        try {
            List<Animal> animals = animalRepository.findAll();
            List<AnimalResponse> response = animals.stream()
                    .map(animal -> new AnimalResponse(
                            animal.getId(),
                            animal.getName(),
                            animal.getAnimal().name(),
                            animal.getBreed(),
                            animal.getAge(),
                            animal.getBehavior().name(),
                            animal.getShelterRequest().getId(),
                            animal.getMedicalFilePath(),
                            animal.getPhotoPath()))
                    .collect(Collectors.toList());

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}