package com.peluditosya.peluditos_ya_server.service;

import com.peluditosya.peluditos_ya_server.dto.ShelterRequestDto;
import com.peluditosya.peluditos_ya_server.model.AppUser;
import com.peluditosya.peluditos_ya_server.model.Role;
import com.peluditosya.peluditos_ya_server.model.ShelterRequest;
import com.peluditosya.peluditos_ya_server.model.ShelterRequestStatus;
import com.peluditosya.peluditos_ya_server.repository.ShelterRequestRepository;
import com.peluditosya.peluditos_ya_server.repository.UserRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class ShelterRequestService {

    private final ShelterRequestRepository shelterRequestRepository;
    private final UserRepository userRepository;
    private final MailService mailService;

    // Crear una solicitud de refugio
    @Transactional
    public String createRequest(ShelterRequestDto requestDto) {
        // Verificar que el usuario exista
        Optional<AppUser> optionalUser = userRepository.findById(requestDto.getUserId());
        if (!optionalUser.isPresent()) {
            return "Usuario no encontrado";
        }
        AppUser user = optionalUser.get();

        // Validar que los campos de la solicitud estén completos
        if (requestDto.getPhone() == null || requestDto.getPhone().isEmpty()) {
            return "El número de teléfono es obligatorio";
        }
        if (requestDto.getShelterAddress() == null || requestDto.getShelterAddress().isEmpty()) {
            return "La dirección del refugio es obligatoria";
        }
        if (requestDto.getShelterName() == null || requestDto.getShelterName().isEmpty()) {
            return "El nombre del refugio es obligatorio";
        }
        if (requestDto.getLatitude() == null || requestDto.getLongitude() == null) {
            return "La ubicación del refugio es obligatoria";
        }

        // Crear la solicitud
        ShelterRequest request = new ShelterRequest();
        request.setUser(user);
        request.setDescription(requestDto.getDescription());
        request.setPhone(requestDto.getPhone());
        request.setShelterAddress(requestDto.getShelterAddress());
        request.setShelterName(requestDto.getShelterName());
        request.setLatitude(requestDto.getLatitude());
        request.setLongitude(requestDto.getLongitude());
        request.setStatus(ShelterRequestStatus.PENDING); // Establecer el estado como PENDING

        shelterRequestRepository.save(request);
        return "Solicitud creada con éxito";
    }

    // Obtener todas las solicitudes pendientes
    public List<ShelterRequest> getPendingRequests() {
        return shelterRequestRepository.findByStatus(ShelterRequestStatus.PENDING);
    }

    // Actualizar el estado de la solicitud (y también actualizar el rol del usuario si es aprobado)
    @Transactional
    public String updateStatus(Long requestId, ShelterRequestStatus status) {
        Optional<ShelterRequest> optionalRequest = shelterRequestRepository.findById(requestId);
        if (!optionalRequest.isPresent()) {
            return "Solicitud no encontrada";
        }

        ShelterRequest request = optionalRequest.get();
        request.setStatus(status);

        // Si se aprueba la solicitud, actualizamos el rol del usuario
        if (ShelterRequestStatus.APPROVED.equals(status)) {
            AppUser user = request.getUser();
            user.setRole(Role.SHELTER);
            userRepository.save(user);

            // Enviar correo de bienvenida al refugio
            mailService.sendWelcomeEmail(user.getEmail(), user.getName(), "SHELTER");
        }

        shelterRequestRepository.save(request);

        // Enviar un correo informando sobre el cambio de estado
        mailService.sendStatusEmail(request.getUser().getEmail(), status.name());
        return "Estado de la solicitud actualizado";
    }

    public List<ShelterRequest> getSheltersByStatus(ShelterRequestStatus status) {
        return shelterRequestRepository.findByStatus(status);
    }

        // Ejemplo de implementación en ShelterRequestService
    public Long getShelterId(Long shelterRequestId) {
        // Consulta la tabla shelter_requests para obtener el shelterId
        Optional<ShelterRequest> shelterRequestOpt = shelterRequestRepository.findById(shelterRequestId);
        if (shelterRequestOpt.isEmpty()) {
            throw new IllegalArgumentException("Shelter request not found");
        }
        return shelterRequestOpt.get().getShelterId();
    }

    public ShelterRequest getShelterRequestById(Long id) {
        return shelterRequestRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("No se encontró un refugio con el ID proporcionado."));
    }

    @Transactional
    public void deleteShelterRequest(Long id) {
        if (!shelterRequestRepository.existsById(id)) {
            throw new IllegalArgumentException("No se encontró una solicitud de refugio con el ID proporcionado.");
        }
        shelterRequestRepository.deleteById(id);
    }
}
