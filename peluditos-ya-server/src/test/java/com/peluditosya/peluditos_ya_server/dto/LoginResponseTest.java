package com.peluditosya.peluditos_ya_server.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class LoginResponseTest {

    @Test
    public void testConstructorAndGetters() {
        LoginResponse response = new LoginResponse("Inicio de sesión exitoso", true);

        assertEquals("Inicio de sesión exitoso", response.getMessage(), "El mensaje debería coincidir");
        assertTrue(response.isAdmin(), "El valor de admin debería ser true");
    }

    @Test
    public void testSetters() {
        LoginResponse response = new LoginResponse("", false);

        response.setMessage("Nuevo mensaje");
        response.setAdmin(true);

        assertEquals("Nuevo mensaje", response.getMessage(), "El mensaje debería haberse actualizado");
        assertTrue(response.isAdmin(), "El valor de admin debería haberse actualizado a true");
    }
}
