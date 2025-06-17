package com.peluditosya.peluditos_ya_server.service;

import com.peluditosya.peluditos_ya_server.dto.AnimalDetailDTO;
import com.peluditosya.peluditos_ya_server.dto.AnimalRegistrationRequest;
import com.peluditosya.peluditos_ya_server.dto.AnimalUpdateRequest;
import com.peluditosya.peluditos_ya_server.exception.ResourceNotFoundException;
import com.peluditosya.peluditos_ya_server.model.Animal;
import com.peluditosya.peluditos_ya_server.model.ShelterRequest;
import com.peluditosya.peluditos_ya_server.model.AppUser;
import com.peluditosya.peluditos_ya_server.repository.AnimalRepository;
import com.peluditosya.peluditos_ya_server.repository.ShelterRequestRepository;
import com.peluditosya.peluditos_ya_server.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.List;

@Service
public class AnimalService {
    private final AnimalRepository animalRepository;
    private final ShelterRequestRepository shelterRequestRepository;
    private final UserRepository userRepository;
    private final FileStorageService fileStorageService;
    private final Logger logger = LoggerFactory.getLogger(AnimalService.class);

    public AnimalService(AnimalRepository animalRepository, FileStorageService fileStorageService,
                        ShelterRequestRepository shelterRequestRepository, UserRepository userRepository) {
        this.animalRepository = animalRepository;
        this.shelterRequestRepository = shelterRequestRepository;
        this.userRepository = userRepository;
        this.fileStorageService = fileStorageService;
    }

    public Animal registerAnimal(AnimalRegistrationRequest request, Long shelterRequestId) {
        logger.info("Iniciando registro de animal. shelterRequestId recibido: {}", shelterRequestId);

        Optional<ShelterRequest> shelterRequestOpt = shelterRequestRepository.findById(shelterRequestId);
        if (shelterRequestOpt.isEmpty()) {
            logger.error("No se encontró un ShelterRequest con shelterRequestId: {}", shelterRequestId);
            throw new IllegalArgumentException("No se encontró un refugio con el ID proporcionado.");
        }
        ShelterRequest shelterRequest = shelterRequestOpt.get();

        String medicalFilePath = fileStorageService.storeFile(request.getMedicalFile(), "medical-files");
        String photoPath = null;
        if (request.getPhoto() != null && !request.getPhoto().isEmpty()) {
            photoPath = fileStorageService.storeFile(request.getPhoto(), "animal-photos");
        } else if (request.getPhotoUrl() != null && !request.getPhotoUrl().isEmpty()) {
            photoPath = request.getPhotoUrl();
        }

        Animal animal = new Animal();
        animal.setName(request.getName());
        animal.setAnimal(Animal.AnimalType.valueOf(request.getAnimal()));
        animal.setBreed(request.getBreed());
        animal.setAge(request.getAge());
        animal.setBehavior(Animal.Behavior.valueOf(request.getBehavior()));
        animal.setMedicalFilePath(medicalFilePath);
        animal.setPhotoPath(photoPath);
        animal.setShelterRequest(shelterRequest);
        // Asegurar que el estado se establezca correctamente
        animal.setStatus("AVAILABLE");

        animalRepository.save(animal);
        logger.info("Nuevo animal registrado: {} en refugio {}", animal.getName(), shelterRequest.getShelterName());

        return animal;
    }

    public List<Animal> obtenerTodasLasMascotas() {
        return animalRepository.findAll();
    }

    public AnimalDetailDTO getAnimalDetails(Long animalId) {
        Animal animal = animalRepository.findById(animalId)
                .orElseThrow(() -> new RuntimeException("Animal not found with id: " + animalId));

        return mapToDetailDTO(animal);
    }

    private AnimalDetailDTO mapToDetailDTO(Animal animal) {
        AnimalDetailDTO dto = new AnimalDetailDTO();
        dto.setId(animal.getId());
        dto.setName(animal.getName());
        dto.setAnimalType(animal.getAnimal().toString());
        dto.setBreed(animal.getBreed());
        dto.setAge(animal.getAge());
        dto.setBehavior(animal.getBehavior().toString());
        dto.setMedicalFilePath(animal.getMedicalFilePath());
        dto.setPhotoPath(animal.getPhotoPath());

        if (animal.getShelterRequest() != null) {
            dto.setShelterId(animal.getShelterRequest().getId());
            dto.setShelterName(animal.getShelterRequest().getShelterName());
            dto.setShelterAddress(animal.getShelterRequest().getShelterAddress());
            dto.setShelterPhone(animal.getShelterRequest().getPhone());
        }

        if (animal.getSponsor() != null) {
            dto.setSponsorId(animal.getSponsor().getId());
        }

        return dto;
    }

    public Animal asignarPadrino(Long animalId, Long userId) {
        Animal animal = animalRepository.findById(animalId)
                .orElseThrow(() -> new RuntimeException("Animal no encontrado con ID: " + animalId));

        AppUser user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID: " + userId));

        animal.setSponsor(user);
        return animalRepository.save(animal);
    }

    public Animal updateAnimal(Long animalId, AnimalUpdateRequest request) {
        logger.info("Iniciando actualización de animal con ID: {}", animalId);

        Animal animal = animalRepository.findById(animalId)
                .orElseThrow(() -> new RuntimeException("Animal no encontrado con ID: " + animalId));

        // Validate basic data before updating
        if (request.getName() != null && request.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre del animal no puede estar vacío");
        }
        
        if (request.getAge() != null && request.getAge() <= 0) {
            throw new IllegalArgumentException("La edad del animal debe ser mayor a 0");
        }

        // Update basic fields
        if (request.getName() != null && !request.getName().trim().isEmpty()) {
            animal.setName(request.getName().trim());
        }
        
        if (request.getAnimal() != null && !request.getAnimal().trim().isEmpty()) {
            animal.setAnimal(Animal.AnimalType.valueOf(request.getAnimal().toUpperCase()));
        }
        
        if (request.getBreed() != null && !request.getBreed().trim().isEmpty()) {
            animal.setBreed(request.getBreed().trim());
        }
        
        if (request.getAge() != null && request.getAge() > 0) {
            animal.setAge(request.getAge());
        }
        
        if (request.getBehavior() != null && !request.getBehavior().trim().isEmpty()) {
            animal.setBehavior(Animal.Behavior.valueOf(request.getBehavior().toUpperCase()));
        }

        // Update detailed fields
        if (request.getMedicalHistory() != null) {
            animal.setMedicalHistory(request.getMedicalHistory().trim());
        }
        
        if (request.getPersonalityTraits() != null) {
            animal.setPersonalityTraits(request.getPersonalityTraits().trim());
        }
        
        if (request.getHabits() != null) {
            animal.setHabits(request.getHabits().trim());
        }

        // Update files if provided
        if (request.getMedicalFile() != null && !request.getMedicalFile().isEmpty()) {
            String medicalFilePath = fileStorageService.storeFile(request.getMedicalFile(), "medical-files");
            animal.setMedicalFilePath(medicalFilePath);
        }

        if (request.getPhoto() != null && !request.getPhoto().isEmpty()) {
            String photoPath = fileStorageService.storeFile(request.getPhoto(), "animal-photos");
            animal.setPhotoPath(photoPath);
        } else if (request.getPhotoUrl() != null && !request.getPhotoUrl().trim().isEmpty()) {
            animal.setPhotoPath(request.getPhotoUrl().trim());
        }

        Animal updatedAnimal = animalRepository.save(animal);
        logger.info("Animal actualizado exitosamente: {}", updatedAnimal.getName());
        
        return updatedAnimal;
    }

    public void deleteAnimal(Long animalId) {
        logger.info("Iniciando eliminación de animal con ID: {}", animalId);
        
        Optional<Animal> animalOpt = animalRepository.findById(animalId);
        if (animalOpt.isEmpty()) {
            logger.error("No se encontró un animal con ID: {}", animalId);
            throw new ResourceNotFoundException("No se encontró un animal con el ID proporcionado.");
        }
        
        Animal animal = animalOpt.get();
        logger.info("Eliminando animal: {}", animal.getName());
        
        animalRepository.delete(animal);
        logger.info("Animal eliminado exitosamente");
    }

    public Animal updateAnimalStatus(Long animalId, String status) {
        logger.info("Actualizando estado del animal con ID: {} a estado: {}", animalId, status);
        
        Optional<Animal> animalOpt = animalRepository.findById(animalId);
        if (animalOpt.isEmpty()) {
            logger.error("No se encontró un animal con ID: {}", animalId);
            throw new ResourceNotFoundException("No se encontró un animal con el ID proporcionado.");
        }
        
        Animal animal = animalOpt.get();
        logger.info("Cambiando estado de {} de {} a {}", animal.getName(), animal.getStatus(), status);
        
        // Ensure the status is valid
        if (status == null || status.trim().isEmpty()) {
            throw new IllegalArgumentException("El estado no puede estar vacío");
        }
        
        try {
            animal.setStatus(status);
            Animal updatedAnimal = animalRepository.save(animal);
            logger.info("Estado del animal actualizado exitosamente");
            return updatedAnimal;
        } catch (Exception e) {
            logger.error("Error al actualizar el estado del animal: ", e);
            throw e;
        }
    }
}
