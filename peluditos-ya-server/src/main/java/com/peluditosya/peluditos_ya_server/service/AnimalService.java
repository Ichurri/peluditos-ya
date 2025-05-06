package com.peluditosya.peluditos_ya_server.service;

import com.peluditosya.peluditos_ya_server.dto.AnimalRegistrationRequest;
import com.peluditosya.peluditos_ya_server.model.Animal;
import com.peluditosya.peluditos_ya_server.model.Shelter;
import com.peluditosya.peluditos_ya_server.repository.AnimalRepository;
import com.peluditosya.peluditos_ya_server.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.List;


@Service
public class AnimalService {
    private final AnimalRepository animalRepository;
    private final UserRepository userRepository;
    private final Logger logger = LoggerFactory.getLogger(AnimalService.class);
    private final FileStorageService fileStorageService;

    public AnimalService(AnimalRepository animalRepository, UserRepository userRepository, FileStorageService fileStorageService) {
        this.animalRepository = animalRepository;
        this.userRepository = userRepository;
        this.fileStorageService = fileStorageService;
    }

    public Animal registerAnimal(AnimalRegistrationRequest request) {
        // Find shelter
        Optional<Shelter> shelterOpt = userRepository.findById(request.getShelterId())
                .filter(Shelter.class::isInstance)
                .map(Shelter.class::cast);
        if (shelterOpt.isEmpty()) {
            throw new IllegalArgumentException("Shelter not found");
        }
        Shelter shelter = shelterOpt.get();

        // Handle file uploads
        String medicalFilePath = fileStorageService.storeFile(request.getMedicalFile(), "medical-files");
        String photoPath = fileStorageService.storeFile(request.getPhoto(), "animal-photos");

        // Build Animal entity
        Animal animal = new Animal();
        animal.setName(request.getName());
        animal.setAnimal(Animal.AnimalType.valueOf(request.getAnimal()));
        animal.setBreed(request.getBreed());
        animal.setAge(request.getAge());
        animal.setBehavior(Animal.Behavior.valueOf(request.getBehavior()));
        animal.setMedicalFilePath(medicalFilePath);
        animal.setPhotoPath(photoPath);
        animal.setShelter(shelter);

        logger.info("Received medicalFile: {}", request.getMedicalFile() != null ? request.getMedicalFile().getOriginalFilename() : "null");
        logger.info("Received photo: {}", request.getPhoto() != null ? request.getPhoto().getOriginalFilename() : "null");

        animalRepository.save(animal);
        logger.info("Nuevo animal registrado: {} en refugio {}", animal.getName(), shelter.getShelterName());
        return animal;
    }

    public List<Animal> obtenerTodasLasMascotas() {
        return animalRepository.findAll();
    }
}
