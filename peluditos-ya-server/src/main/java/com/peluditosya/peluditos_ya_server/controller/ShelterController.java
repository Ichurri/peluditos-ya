package com.peluditosya.peluditos_ya_server.controller;

import com.peluditosya.peluditos_ya_server.dto.ShelterRequestDto;
import com.peluditosya.peluditos_ya_server.model.Shelter;
import com.peluditosya.peluditos_ya_server.service.ShelterService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/shelters")
@CrossOrigin(origins = "http://localhost:4200")
public class ShelterController {

    private final ShelterService shelterService;
    private final Logger logger = LoggerFactory.getLogger(ShelterController.class);

    public ShelterController(ShelterService shelterService) {
        this.shelterService = shelterService;
    }

    @GetMapping
    public List<Shelter> getAllShelters() {
        return shelterService.getAllShelters();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteShelter(@PathVariable Long id) {
        logger.info("Deleting shelter with id: {}", id);
        shelterService.deleteShelter(id);
        return ResponseEntity.noContent().build(); // 204 No Content
    }
}