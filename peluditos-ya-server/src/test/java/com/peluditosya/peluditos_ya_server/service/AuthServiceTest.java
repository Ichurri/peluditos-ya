package com.peluditosya.peluditos_ya_server.service;

import com.peluditosya.peluditos_ya_server.dto.LoginRequest;
import com.peluditosya.peluditos_ya_server.model.AppUser;
import com.peluditosya.peluditos_ya_server.model.Role;
import com.peluditosya.peluditos_ya_server.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Map;

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
    public void testLoginSuccess() {
        // Arrange: configuramos los datos de entrada y el comportamiento del repositorio
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail("test@example.com");
        loginRequest.setPassword("password123");
        
        AppUser user = new AppUser();
        user.setEmail("test@example.com");
        user.setPassword("password123");
        // Asumimos que se trata de un usuario ADMIN para comprobar la bandera 'admin'
        user.setRole(Role.ADMIN);
        
        Mockito.when(userRepository.findByEmailAndPassword("test@example.com", "password123"))
               .thenReturn(user);
        
        // Act: llamamos al método de login
        Map<String, Object> response = authService.login(loginRequest);
        
        // Assert: verificamos que la respuesta contenga los datos esperados
        assertNotNull(response);
        assertEquals("Login exitoso", response.get("message"));
        assertEquals(Role.ADMIN, response.get("role"));
        assertTrue((Boolean) response.get("admin"));
    }
    
    @Test
    public void testLoginFailure() {
        // Arrange: definimos credenciales equivocadas
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail("wrong@example.com");
        loginRequest.setPassword("wrongpass");
        
        Mockito.when(userRepository.findByEmailAndPassword("wrong@example.com", "wrongpass"))
               .thenReturn(null);
        
        // Act & Assert: se debe lanzar la excepción de credenciales inválidas
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            authService.login(loginRequest);
        });
        assertEquals("Credenciales inválidas", exception.getMessage());
    }
}
