package com.peluditosya.peluditos_ya_server.controller;

import com.peluditosya.peluditos_ya_server.model.AppUser;

import com.peluditosya.peluditos_ya_server.dto.ShelterRegistrationRequest;
import com.peluditosya.peluditos_ya_server.model.Shelter;
import com.peluditosya.peluditos_ya_server.model.ShelterDocument;
import com.peluditosya.peluditos_ya_server.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/shelters")
@CrossOrigin(origins = "http://localhost:4200")
public class ShelterController {

    @Value("${file.upload-dir}")
    private String uploadDir;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/register")
    public ResponseEntity<?> registerShelter(
        @RequestPart("data") ShelterRegistrationRequest request,
        @RequestPart("legalId") MultipartFile legalIdDoc,
        @RequestPart("addressProof") MultipartFile addressProofDoc) {
        
        try {
            // 1. Crear entidad Shelter
            Shelter shelter = new Shelter();
            shelter.setName(request.getLegalName());
            shelter.setRole(AppUser.UserRole.REFUGIO);
            shelter.setAddress(request.getAddress());
            
            // 2. Guardar documentos
            ShelterDocument legalDoc = saveDocument(legalIdDoc, "IDENTIFICACION_LEGAL");
            ShelterDocument addressDoc = saveDocument(addressProofDoc, "COMPROBANTE_DOMICILIO");
            
            shelter.setDocuments(List.of(legalDoc, addressDoc));
            
            // 3. Guardar en base de datos
            userRepository.save(shelter);
            
            return ResponseEntity.status(201).body("Solicitud de refugio registrada");
            
        } catch (IOException e) {
            return ResponseEntity.badRequest().body("Error al procesar documentos");
        }
    }

    private ShelterDocument saveDocument(MultipartFile file, String docType) throws IOException {
        String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
        Path filePath = Paths.get(uploadDir, fileName);
        Files.copy(file.getInputStream(), filePath);
        
        ShelterDocument doc = new ShelterDocument();
        doc.setDocumentType(docType);
        doc.setFilePath(filePath.toString());
        return doc;
    }
}