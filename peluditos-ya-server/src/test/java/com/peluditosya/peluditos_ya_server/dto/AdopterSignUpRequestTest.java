package com.peluditosya.peluditos_ya_server.dto;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class AdopterSignUpRequestTest {

    private static Validator validator;

    @BeforeAll
    public static void setup() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    private AdopterSignUpRequest crearValido() {
        AdopterSignUpRequest request = new AdopterSignUpRequest();
        request.setName("Lucía Gómez");
        request.setEmail("lucia@example.com");
        request.setPassword("contrasenaSegura");
        request.setCity("Bogotá");
        request.setIsAdmin(false);
        request.setPhone("3001234567");
        return request;
    }

    @Test
    public void testValidRequest() {
        AdopterSignUpRequest request = crearValido();
        Set<ConstraintViolation<AdopterSignUpRequest>> violations = validator.validate(request);
        assertTrue(violations.isEmpty(), "El objeto AdopterSignUpRequest debería ser válido");
    }

    @Test
    public void testEmptyPhone() {
        AdopterSignUpRequest request = crearValido();
        request.setPhone("");

        Set<ConstraintViolation<AdopterSignUpRequest>> violations = validator.validate(request);
        assertFalse(violations.isEmpty(), "El teléfono no puede estar vacío");
    }

    @Test
    public void testPhoneWithLetters() {
        AdopterSignUpRequest request = crearValido();
        request.setPhone("abc123");

        Set<ConstraintViolation<AdopterSignUpRequest>> violations = validator.validate(request);
        assertFalse(violations.isEmpty(), "El teléfono solo debe contener números");
    }
}
