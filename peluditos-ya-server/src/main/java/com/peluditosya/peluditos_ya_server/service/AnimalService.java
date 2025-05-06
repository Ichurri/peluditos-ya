package com.peluditosya.peluditos_ya_server.service;

import com.peluditosya.peluditos_ya_server.dto.AnimalRegistrationRequest;
import com.peluditosya.peluditos_ya_server.model.Animal;
import com.peluditosya.peluditos_ya_server.model.Shelter;
import com.peluditosya.peluditos_ya_server.model.ShelterRequest;
import com.peluditosya.peluditos_ya_server.repository.AnimalRepository;
import com.peluditosya.peluditos_ya_server.repository.UserRepository;
import com.peluditosya.peluditos_ya_server.repository.ShelterRequestRepository;
import com.peluditosya.peluditos_ya_server.service.ShelterRequestService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.List;


@Service
public class AnimalService {
    private final AnimalRepository animalRepository;
    private final ShelterRequestRepository ShelterRequestRepository;
    private final Logger logger = LoggerFactory.getLogger(AnimalService.class);
    private final FileStorageService fileStorageService;

    public AnimalService(AnimalRepository animalRepository, FileStorageService fileStorageService, ShelterRequestRepository ShelterRequestRepository) {
        this.animalRepository = animalRepository;
        this.ShelterRequestRepository = ShelterRequestRepository;
        this.fileStorageService = fileStorageService;
    }

    public Animal registerAnimal(AnimalRegistrationRequest request, Long shelterRequestId) {
        logger.info("Iniciando registro de animal. shelterRequestId recibido: {}", shelterRequestId);
    
        // Buscar el ShelterRequest en la tabla shelter_requests
        Optional<ShelterRequest> shelterRequestOpt = ShelterRequestRepository.findById(shelterRequestId);
        if (shelterRequestOpt.isEmpty()) {
            logger.error("No se encontró un ShelterRequest con shelterRequestId: {}", shelterRequestId);
            throw new IllegalArgumentException("No se encontró un refugio con el ID proporcionado.");
        }
        ShelterRequest shelterRequest = shelterRequestOpt.get();
    
        // Log para verificar los datos del ShelterRequest
        logger.info("Datos del ShelterRequest encontrado: Name: {}, Address: {}, Phone: {}",
                shelterRequest.getShelterName(), shelterRequest.getShelterAddress(), shelterRequest.getPhone());
    
        // Manejo de archivos subidos
        String medicalFilePath = fileStorageService.storeFile(request.getMedicalFile(), "medical-files");
        String photoPath = fileStorageService.storeFile(request.getPhoto(), "animal-photos");
    
        // Construir la entidad Animal
        Animal animal = new Animal();
        animal.setName(request.getName());
        animal.setAnimal(Animal.AnimalType.valueOf(request.getAnimal()));
        animal.setBreed(request.getBreed());
        animal.setAge(request.getAge());
        animal.setBehavior(Animal.Behavior.valueOf(request.getBehavior()));
        animal.setMedicalFilePath(medicalFilePath);
        animal.setPhotoPath(photoPath);
    
        // Asignar el ShelterRequest al animal
        animal.setShelterRequest(shelterRequest);
    
        logger.info("Datos del animal a registrar: Name: {}, AnimalType: {}, Breed: {}, Age: {}, Behavior: {}, ShelterName: {}, ShelterAddress: {}, ShelterPhone: {}",
                animal.getName(), animal.getAnimal(), animal.getBreed(), animal.getAge(), animal.getBehavior(),
                shelterRequest.getShelterName(), shelterRequest.getShelterAddress(), shelterRequest.getPhone());
    
        // Guardar el animal en la base de datos
        animalRepository.save(animal);
        logger.info("Nuevo animal registrado: {} en refugio {}", animal.getName(), shelterRequest.getShelterName());
    
        return animal;
    }

    public List<Animal> obtenerTodasLasMascotas() {
        return animalRepository.findAll();
    }
}
