// src/main/java/com/hilr/peluditos_ya_server/controller/MascotaController.java
package com.hilr.peluditos_ya_server.controller;

import com.hilr.peluditos_ya_server.model.Mascota;
import com.hilr.peluditos_ya_server.service.MascotaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/mascotas")
@CrossOrigin(origins = "http://localhost:4200") // For Angular frontend
public class MascotaController {

    private final MascotaService mascotaService;

    @Autowired
    public MascotaController(MascotaService mascotaService) {
        this.mascotaService = mascotaService;
    }

    @GetMapping
    public ResponseEntity<List<Mascota>> getAllMascotas() {
        List<Mascota> mascotas = mascotaService.findAllMascotas();
        return new ResponseEntity<>(mascotas, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Mascota> getMascotaById(@PathVariable Long id) {
        Optional<Mascota> mascota = mascotaService.findMascotaById(id);
        return mascota.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<Mascota> createMascota(@RequestBody Mascota mascota) {
        Mascota savedMascota = mascotaService.saveMascota(mascota);
        return new ResponseEntity<>(savedMascota, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Mascota> updateMascota(@PathVariable Long id, @RequestBody Mascota mascota) {
        Optional<Mascota> existingMascota = mascotaService.findMascotaById(id);

        if (existingMascota.isPresent()) {
            mascota.setId(id);
            Mascota updatedMascota = mascotaService.saveMascota(mascota);
            return new ResponseEntity<>(updatedMascota, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteMascota(@PathVariable Long id) {
        try {
            mascotaService.deleteMascota(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/especie/{especie}")
    public ResponseEntity<List<Mascota>> getMascotasByEspecie(@PathVariable String especie) {
        List<Mascota> mascotas = mascotaService.findByEspecie(especie);
        return new ResponseEntity<>(mascotas, HttpStatus.OK);
    }

    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<Mascota>> getMascotasByEstado(@PathVariable String estado) {
        List<Mascota> mascotas = mascotaService.findByEstado(estado);
        return new ResponseEntity<>(mascotas, HttpStatus.OK);
    }

    @GetMapping("/filtro")
    public ResponseEntity<List<Mascota>> getMascotasByEspecieAndEstado(
            @RequestParam String especie,
            @RequestParam String estado) {
        List<Mascota> mascotas = mascotaService.findByEspecieAndEstado(especie, estado);
        return new ResponseEntity<>(mascotas, HttpStatus.OK);
    }
}