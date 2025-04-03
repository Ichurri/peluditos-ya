package com.peluditosya.peluditos_ya_server.controller;

import com.peluditosya.peluditos_ya_server.model.Shelter;
import com.peluditosya.peluditos_ya_server.dto.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.peluditosya.peluditos_ya_server.repository.UserRepository;

import java.util.List;

@RestController
@RequestMapping("/api/shelters")
@CrossOrigin(origins = "http://localhost:4200") 
public class ShelterController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public List<Shelter> getAllShelters() {
        return userRepository.findByRole(Role.SHELTER);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteShelter(@PathVariable Long id) {
        try {
            userRepository.deleteById(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error al eliminar el refugio");
        }
    }
}