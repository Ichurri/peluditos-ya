package com.peluditosya.peluditos_ya_server.dto;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class LoginRequestTest {

    private static Validator validator;

    @BeforeAll
    public static void setup() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void testValidLoginRequest() {
        LoginRequest request = new LoginRequest();
        request.setEmail("test@example.com");
        request.setPassword("securePass123");

        Set<ConstraintViolation<LoginRequest>> violations = validator.validate(request);
        assertTrue(violations.isEmpty(), "El objeto LoginRequest debería ser válido");
    }

    @Test
    public void testInvalidEmail() {
        LoginRequest request = new LoginRequest();
        request.setEmail("invalid-email");
        request.setPassword("securePass123");

        Set<ConstraintViolation<LoginRequest>> violations = validator.validate(request);
        assertFalse(violations.isEmpty(), "El email debe ser inválido");
    }

    @Test
    public void testEmptyEmail() {
        LoginRequest request = new LoginRequest();
        request.setEmail("");
        request.setPassword("securePass123");

        Set<ConstraintViolation<LoginRequest>> violations = validator.validate(request);
        assertFalse(violations.isEmpty(), "El email no puede estar vacío");
    }

    @Test
    public void testEmptyPassword() {
        LoginRequest request = new LoginRequest();
        request.setEmail("test@example.com");
        request.setPassword("");

        Set<ConstraintViolation<LoginRequest>> violations = validator.validate(request);
        assertFalse(violations.isEmpty(), "La contraseña no puede estar vacía");
    }

    @Test
    public void testShortPassword() {
        LoginRequest request = new LoginRequest();
        request.setEmail("test@example.com");
        request.setPassword("123");

        Set<ConstraintViolation<LoginRequest>> violations = validator.validate(request);
        assertFalse(violations.isEmpty(), "La contraseña debe tener al menos 6 caracteres");
    }
}
