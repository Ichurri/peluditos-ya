package com.peluditosya.peluditos_ya_server.controller;

import com.peluditosya.peluditos_ya_server.dto.ShelterRequestDto;
import com.peluditosya.peluditos_ya_server.dto.ShelterUpdateRequest;
import com.peluditosya.peluditos_ya_server.model.ShelterRequest;
import com.peluditosya.peluditos_ya_server.model.ShelterRequestStatus;
import com.peluditosya.peluditos_ya_server.service.ShelterRequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/shelter-requests")
@RequiredArgsConstructor
public class ShelterRequestController {

    private final ShelterRequestService shelterRequestService;

    @PostMapping
    public ResponseEntity<String> createRequest(@RequestBody ShelterRequestDto requestDto) {
        // Validar el DTO si es necesario (puedes añadir validación aquí)
        shelterRequestService.createRequest(requestDto);
        return ResponseEntity.ok("Solicitud enviada con éxito");
    }

    @GetMapping("/pending")
    public ResponseEntity<List<ShelterRequest>> getPendingRequests() {
        List<ShelterRequest> pendingRequests = shelterRequestService.getPendingRequests();
        return ResponseEntity.ok(pendingRequests);
    }

    @GetMapping("/approved")
    public List<ShelterRequest> getApprovedShelters() {
        return shelterRequestService.getSheltersByStatus(ShelterRequestStatus.APPROVED);
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<String> updateStatus(@PathVariable Long id, @RequestParam ShelterRequestStatus status) {
        shelterRequestService.updateStatus(id, status);
        return ResponseEntity.ok("Estado actualizado con éxito");
    }

    @GetMapping("/{id}")
    public ResponseEntity<ShelterRequest> getShelterRequestById(@PathVariable Long id) {
        ShelterRequest shelterRequest = shelterRequestService.getShelterRequestById(id);
        return ResponseEntity.ok(shelterRequest);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ShelterRequest> updateShelterRequest(@PathVariable Long id,
                                                              @ModelAttribute ShelterUpdateRequest request) {
        try {
            ShelterRequest updatedShelter = shelterRequestService.updateShelterRequest(id, request);
            return ResponseEntity.ok(updatedShelter);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteShelterRequest(@PathVariable Long id) {
        shelterRequestService.deleteShelterRequest(id);
        return ResponseEntity.ok("Solicitud de refugio eliminada con éxito");
    }
}
