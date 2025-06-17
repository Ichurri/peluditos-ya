package com.peluditosya.peluditos_ya_server.controller;

import com.peluditosya.peluditos_ya_server.dto.AnimalDetailDTO;
import com.peluditosya.peluditos_ya_server.dto.AnimalRegistrationRequest;
import com.peluditosya.peluditos_ya_server.dto.AnimalResponse;
import com.peluditosya.peluditos_ya_server.dto.AnimalUpdateRequest;
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
                        animal.getAnimal().toString(),
                        animal.getBreed(),
                        animal.getAge(),
                        animal.getBehavior().toString(),
                        animal.getShelterRequest() != null ? animal.getShelterRequest().getId() : null,
                        animal.getShelterRequest() != null ? animal.getShelterRequest().getShelterName() : null,
                        animal.getShelterRequest() != null ? animal.getShelterRequest().getShelterAddress() : null,
                        animal.getShelterRequest() != null ? animal.getShelterRequest().getPhone() : null,
                        animal.getMedicalFilePath(),
                        animal.getPhotoPath(),
                        animal.getStatus(),
                        animal.getSponsor() != null ? animal.getSponsor().getId() : null,
                        animal.getSponsor() != null ? animal.getSponsor().getName() : null,
                        animal.getSponsor() != null ? animal.getSponsor().getEmail() : null
                ))
                .collect(Collectors.toList());

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }


    @GetMapping("/{id}/profile")
    public ResponseEntity<AnimalDetailDTO> getAnimalProfile(@PathVariable Long id) {
        AnimalDetailDTO animalDetail = animalService.getAnimalDetails(id);
        return ResponseEntity.ok(animalDetail);
    }

    @PutMapping("/{id}/asignar-padrino")
    public ResponseEntity<AnimalDetailDTO> asignarPadrino(@PathVariable Long id, @RequestParam Long userId) {
        Animal animalActualizado = animalService.asignarPadrino(id, userId);
        return ResponseEntity.ok(animalService.getAnimalDetails(animalActualizado.getId()));
    }

    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<AnimalDetailDTO> updateAnimal(@PathVariable Long id, @ModelAttribute AnimalUpdateRequest request) {
        try {
            Animal updatedAnimal = animalService.updateAnimal(id, request);
            AnimalDetailDTO animalDetail = animalService.getAnimalDetails(updatedAnimal.getId());
            return ResponseEntity.ok(animalDetail);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteAnimal(@PathVariable Long id) {
        try {
            animalService.deleteAnimal(id);
            return ResponseEntity.ok("Animal eliminado exitosamente");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al eliminar el animal");
        }
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<AnimalResponse> updateAnimalStatus(@PathVariable Long id, @RequestBody StatusUpdateRequest request) {
        try {
            Animal updatedAnimal = animalService.updateAnimalStatus(id, request.getStatus());
            AnimalResponse response = new AnimalResponse(
                    updatedAnimal.getId(),
                    updatedAnimal.getName(),
                    updatedAnimal.getAnimal().toString(),
                    updatedAnimal.getBreed(),
                    updatedAnimal.getAge(),
                    updatedAnimal.getBehavior().toString(),
                    updatedAnimal.getShelterRequest() != null ? updatedAnimal.getShelterRequest().getId() : null,
                    updatedAnimal.getShelterRequest() != null ? updatedAnimal.getShelterRequest().getShelterName() : null,
                    updatedAnimal.getShelterRequest() != null ? updatedAnimal.getShelterRequest().getShelterAddress() : null,
                    updatedAnimal.getShelterRequest() != null ? updatedAnimal.getShelterRequest().getPhone() : null,
                    updatedAnimal.getMedicalFilePath(),
                    updatedAnimal.getPhotoPath(),
                    updatedAnimal.getStatus(),
                    updatedAnimal.getSponsor() != null ? updatedAnimal.getSponsor().getId() : null,
                    updatedAnimal.getSponsor() != null ? updatedAnimal.getSponsor().getName() : null,
                    updatedAnimal.getSponsor() != null ? updatedAnimal.getSponsor().getEmail() : null
            );
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    // DTO class for status update requests
    public static class StatusUpdateRequest {
        private String status;

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }
    }
}