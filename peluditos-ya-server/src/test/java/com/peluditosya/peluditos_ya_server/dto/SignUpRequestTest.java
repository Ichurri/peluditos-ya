// package com.peluditosya.peluditos_ya_server.dto;

// import jakarta.validation.ConstraintViolation;
// import jakarta.validation.Validation;
// import jakarta.validation.Validator;
// import jakarta.validation.ValidatorFactory;
// import org.junit.jupiter.api.BeforeAll;
// import org.junit.jupiter.api.Test;

// import java.util.Set;

// import static org.junit.jupiter.api.Assertions.*;

// public class SignUpRequestTest {

//     private static Validator validator;

//     @BeforeAll
//     public static void setup() {
//         ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
//         validator = factory.getValidator();
//     }

//     @Test
//     public void testValidSignUpRequest() {
//         SignUpRequest request = new SignUpRequest();
//         request.setName("Juan Pérez");
//         request.setEmail("juan@example.com");
//         request.setPassword("securePass123");
//         request.setCity("Bogotá");
//         request.setPhone("1234567890");

//         Set<ConstraintViolation<SignUpRequest>> violations = validator.validate(request);
//         assertTrue(violations.isEmpty(), "El objeto SignUpRequest debería ser válido");
//     }

//     @Test
//     public void testInvalidEmail() {
//         SignUpRequest request = new SignUpRequest();
//         request.setName("Juan Pérez");
//         request.setEmail("invalid-email");
//         request.setPassword("securePass123");
//         request.setCity("Bogotá");
//         request.setPhone("1234567890");

//         Set<ConstraintViolation<SignUpRequest>> violations = validator.validate(request);
//         assertFalse(violations.isEmpty(), "El email debe ser inválido");
//     }

//     @Test
//     public void testShortPassword() {
//         SignUpRequest request = new SignUpRequest();
//         request.setName("Juan Pérez");
//         request.setEmail("juan@example.com");
//         request.setPassword("123");
//         request.setCity("Bogotá");
//         request.setPhone("1234567890");

//         Set<ConstraintViolation<SignUpRequest>> violations = validator.validate(request);
//         assertFalse(violations.isEmpty(), "La contraseña debe tener al menos 6 caracteres");
//     }

//     @Test
//     public void testEmptyFields() {
//         SignUpRequest request = new SignUpRequest();
//         request.setName("");
//         request.setEmail("");
//         request.setPassword("");
//         request.setCity("");
//         request.setPhone("");

//         Set<ConstraintViolation<SignUpRequest>> violations = validator.validate(request);
//         assertFalse(violations.isEmpty(), "Todos los campos obligatorios deben generar errores");
//     }

//     @Test
//     public void testInvalidPhoneNumber() {
//         SignUpRequest request = new SignUpRequest();
//         request.setName("Juan Pérez");
//         request.setEmail("juan@example.com");
//         request.setPassword("securePass123");
//         request.setCity("Bogotá");
//         request.setPhone("1234"); // Número demasiado corto

//         Set<ConstraintViolation<SignUpRequest>> violations = validator.validate(request);
//         assertFalse(violations.isEmpty(), "El teléfono debe contener exactamente 10 dígitos");
//     }
// }
