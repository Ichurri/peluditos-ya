package com.peluditosya.peluditos_ya_server.service;

import com.peluditosya.peluditos_ya_server.dto.AnimalRegistrationRequest;
import com.peluditosya.peluditos_ya_server.model.Animal;
import com.peluditosya.peluditos_ya_server.model.Shelter;
import com.peluditosya.peluditos_ya_server.repository.AnimalRepository;
import com.peluditosya.peluditos_ya_server.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

@Service
public class AnimalService {
    private final AnimalRepository animalRepository;
    private final UserRepository userRepository;
    private final Logger logger = LoggerFactory.getLogger(AnimalService.class);

    public AnimalService(AnimalRepository animalRepository, UserRepository userRepository) {
        this.animalRepository = animalRepository;
        this.userRepository = userRepository;
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
        String medicalFilePath = saveFile(request.getMedicalFile(), "medical-files");
        String photoPath = saveFile(request.getPhoto(), "animal-photos");

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

    private String saveFile(MultipartFile file, String subfolder) {
        if (file == null || file.isEmpty()) return null;
        try {
            String uploadDir = System.getProperty("user.dir") + "/uploads/" + subfolder;
            Files.createDirectories(Paths.get(uploadDir));
            String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
            Path filePath = Paths.get(uploadDir, fileName);
            file.transferTo(filePath.toFile());
            return filePath.toString();
        } catch (IOException e) {
            logger.error("Error al guardar archivo {}", file.getOriginalFilename(), e);
            throw new RuntimeException("No se pudo guardar el archivo: " + file.getOriginalFilename());
        }
    }
}
