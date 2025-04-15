package com.peluditosya.peluditos_ya_server.service;
import com.peluditosya.peluditos_ya_server.dto.AdopterSignUpRequest;
import com.peluditosya.peluditos_ya_server.dto.LoginRequest;
import com.peluditosya.peluditos_ya_server.model.AppUser;
import com.peluditosya.peluditos_ya_server.model.Role;
import com.peluditosya.peluditos_ya_server.repository.UserRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Map;

@ExtendWith(MockitoExtension.class)
public class AuthServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private AuthService authService;

    //1. signupAdopter - correo ya está en uso
    @Test
    public void testSignupAdopter_EmailAlreadyExists_ThrowsException() {
        AdopterSignUpRequest request = new AdopterSignUpRequest();
        request.setEmail("correo@yaexiste.com");

        when(userRepository.existsByEmail("correo@yaexiste.com")).thenReturn(true);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            authService.signupAdopter(request);
        });

        assertEquals("El correo ya está en uso", exception.getMessage());
        verify(userRepository, never()).save(any());
    }

    //2. login - credenciales válidas
    @Test
    public void testLogin_ValidCredentials_ReturnsCorrectRole() {
        LoginRequest request = new LoginRequest();
        request.setEmail("usuario@example.com");
        request.setPassword("1234");

        AppUser user = new AppUser();
        user.setEmail("usuario@example.com");
        user.setPassword("1234");
        user.setRole(Role.ADOPTER);

        when(userRepository.findByEmailAndPassword("usuario@example.com", "1234")).thenReturn(user);

        Map<String, Object> response = authService.login(request);

        assertEquals("Login exitoso", response.get("message"));
        assertEquals(Role.ADOPTER, response.get("role"));
        assertEquals(false, response.get("admin"));
    }
}
