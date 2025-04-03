package com.peluditosya.peluditos_ya_server.dto;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class ShelterSignUpRequestTest {

    private static Validator validator;

    @BeforeAll
    public static void setup() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    private ShelterSignUpRequest crearValido() {
        ShelterSignUpRequest request = new ShelterSignUpRequest();
        request.setName("Refugio Esperanza");
        request.setEmail("refugio@correo.com");
        request.setPassword("contrasenaSegura");
        request.setCity("Medellín");
        request.setIsAdmin(false);
        request.setPhone("3014567890");
        request.setDocumentNumber("123456789");
        request.setShelterName("Refugio Esperanza");
        request.setShelterAddress("Calle 123 #45-67");
        return request;
    }

    @Test
    public void testValidRequest() {
        ShelterSignUpRequest request = crearValido();
        Set<ConstraintViolation<ShelterSignUpRequest>> violations = validator.validate(request);
        assertTrue(violations.isEmpty(), "El objeto ShelterSignUpRequest debería ser válido");
    }

    @Test
    public void testEmptyFields() {
        ShelterSignUpRequest request = crearValido();
        request.setPhone("");
        request.setDocumentNumber("");
        request.setShelterName("");
        request.setShelterAddress("");

        Set<ConstraintViolation<ShelterSignUpRequest>> violations = validator.validate(request);
        assertFalse(violations.isEmpty(), "Los campos obligatorios no pueden estar vacíos");
    }

    @Test
    public void testInvalidPhoneNumber() {
        ShelterSignUpRequest request = crearValido();
        request.setPhone("abc123");

        Set<ConstraintViolation<ShelterSignUpRequest>> violations = validator.validate(request);
        assertFalse(violations.isEmpty(), "El teléfono debe contener solo números");
    }

    @Test
    public void testShortDocumentNumber() {
        ShelterSignUpRequest request = crearValido();
        request.setDocumentNumber("12"); // Menos de 4 caracteres

        Set<ConstraintViolation<ShelterSignUpRequest>> violations = validator.validate(request);
        assertFalse(violations.isEmpty(), "El número de documento debe tener al menos 4 caracteres");
    }
}
