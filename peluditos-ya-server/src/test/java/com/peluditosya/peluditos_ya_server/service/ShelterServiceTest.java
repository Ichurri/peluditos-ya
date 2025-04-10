package com.peluditosya.peluditos_ya_server.service;

import com.peluditosya.peluditos_ya_server.model.Shelter;
import com.peluditosya.peluditos_ya_server.model.Role;
import com.peluditosya.peluditos_ya_server.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ShelterServiceTest {

    private UserRepository userRepository;
    private ShelterService shelterService;

    @BeforeEach
    public void setUp() {
        userRepository = Mockito.mock(UserRepository.class);
        shelterService = new ShelterService(userRepository);
    }

    @Test
    public void testGetAllShelters() {
        // Arrange: creamos una lista simulada de refugios
        Shelter shelter1 = new Shelter();
        shelter1.setEmail("shelter1@test.com");
        Shelter shelter2 = new Shelter();
        shelter2.setEmail("shelter2@test.com");
        List<Shelter> shelters = Arrays.asList(shelter1, shelter2);

        Mockito.when(userRepository.findSheltersByRole(Role.SHELTER))
               .thenReturn(shelters);

        // Act: invocamos el método que obtiene los refugios
        List<Shelter> result = shelterService.getAllShelters();

        // Assert: comprobamos que se obtenga la lista esperada
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("shelter1@test.com", result.get(0).getEmail());
    }
}
