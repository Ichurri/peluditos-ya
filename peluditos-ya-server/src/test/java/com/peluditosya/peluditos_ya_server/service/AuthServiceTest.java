package com.peluditosya.peluditos_ya_server.service;

import com.peluditosya.peluditos_ya_server.dto.ShelterSignUpRequest;
import com.peluditosya.peluditos_ya_server.model.Shelter;
import com.peluditosya.peluditos_ya_server.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;

public class AuthServiceTest {

    private UserRepository userRepository;
    private AuthService authService;

    @BeforeEach
    public void setUp() {
        userRepository = Mockito.mock(UserRepository.class);
        authService = new AuthService(userRepository);
    }
    
    @Test
    public void testSignupShelterSuccess() {
        // Arrange: Preparamos la solicitud con datos válidos
        ShelterSignUpRequest request = new ShelterSignUpRequest();
        request.setName("Shelter One");
        request.setEmail("shelter@example.com");
        request.setPassword("securePassword");
        request.setCity("Ciudad");
        request.setPhone("1234567890");
        request.setDocumentNumber("DOC12345");
        request.setShelterName("Refugio Central");
        request.setShelterAddress("Calle Principal 123");
        
        // Simulamos que el email no existe aún
        Mockito.when(userRepository.existsByEmail("shelter@example.com")).thenReturn(false);
        
        // Act: llamamos al método signupShelter
        String response = authService.signupShelter(request);
        
        // Assert: Comprobamos que se registra el refugio correctamente
        assertEquals("Refugio registrado", response);
        // Verificamos que se haya llamado a save con algún objeto de tipo Shelter
        Mockito.verify(userRepository).save(Mockito.any(Shelter.class));
    }
    
    @Test
    public void testSignupShelterDuplicateEmail() {
        // Arrange: Preparamos una solicitud con un email que ya existe
        ShelterSignUpRequest request = new ShelterSignUpRequest();
        request.setName("Shelter Duplicate");
        request.setEmail("duplicate@example.com");
        request.setPassword("securePassword");
        request.setCity("Ciudad");
        request.setPhone("0987654321");
        request.setDocumentNumber("DOC54321");
        request.setShelterName("Refugio Duplicado");
        request.setShelterAddress("Calle Secundaria 456");
        
        // Simulamos que el email ya se encuentra registrado
        Mockito.when(userRepository.existsByEmail("duplicate@example.com")).thenReturn(true);
        
        // Act & Assert: Se debe lanzar la excepción correspondiente
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            authService.signupShelter(request);
        });
        assertEquals("El correo ya está en uso", exception.getMessage());
    }
}
