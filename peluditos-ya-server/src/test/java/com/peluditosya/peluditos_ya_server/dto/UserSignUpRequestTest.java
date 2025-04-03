package com.peluditosya.peluditos_ya_server.dto;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class UserSignUpRequestTest {

    private static Validator validator;

    @BeforeAll
    public static void setup() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    private UserSignUpRequest crearValido() {
        UserSignUpRequest request = new UserSignUpRequest();
        request.setName("Juan Pérez");
        request.setEmail("juan@example.com");
        request.setPassword("securePass123");
        request.setCity("Bogotá");
        request.setIsAdmin(false);
        return request;
    }

    @Test
    public void testValidUserRequest() {
        UserSignUpRequest request = crearValido();
        Set<ConstraintViolation<UserSignUpRequest>> violations = validator.validate(request);
        assertTrue(violations.isEmpty(), "El objeto UserSignUpRequest debería ser válido");
    }

    @Test
    public void testInvalidEmail() {
        UserSignUpRequest request = crearValido();
        request.setEmail("correo-inválido");

        Set<ConstraintViolation<UserSignUpRequest>> violations = validator.validate(request);
        assertFalse(violations.isEmpty(), "El email debe tener formato válido");
    }

    @Test
    public void testShortPassword() {
        UserSignUpRequest request = crearValido();
        request.setPassword("123");

        Set<ConstraintViolation<UserSignUpRequest>> violations = validator.validate(request);
        assertFalse(violations.isEmpty(), "La contraseña debe tener al menos 6 caracteres");
    }

    @Test
    public void testEmptyNameAndCity() {
        UserSignUpRequest request = crearValido();
        request.setName("");
        request.setCity("");

        Set<ConstraintViolation<UserSignUpRequest>> violations = validator.validate(request);
        assertFalse(violations.isEmpty(), "El nombre y la ciudad no pueden estar vacíos");
    }
}
