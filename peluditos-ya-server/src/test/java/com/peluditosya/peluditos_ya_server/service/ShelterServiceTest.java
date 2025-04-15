package com.peluditosya.peluditos_ya_server.service;

import com.peluditosya.peluditos_ya_server.model.Role;
import com.peluditosya.peluditos_ya_server.model.Shelter;
import com.peluditosya.peluditos_ya_server.repository.UserRepository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.mockito.Mockito.*;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ShelterServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private ShelterService shelterService;

    @Test
    void shouldReturnAllShelters() {
        // Arrange
        Shelter shelter1 = new Shelter();
        shelter1.setName("Refugio A");
        Shelter shelter2 = new Shelter();
        shelter2.setName("Refugio B");

        when(userRepository.findSheltersByRole(Role.SHELTER))
                .thenReturn(Arrays.asList(shelter1, shelter2));

        // Act
        List<Shelter> result = shelterService.getAllShelters();

        // Assert
        assertEquals(2, result.size());
        assertEquals("Refugio A", result.get(0).getName());
        assertEquals("Refugio B", result.get(1).getName());
        verify(userRepository, times(1)).findSheltersByRole(Role.SHELTER);
    }

    @Test
    void shouldDeleteShelterById() {
        // Arrange
        Long id = 10L;

        // Act
        shelterService.deleteShelter(id);

        // Assert
        verify(userRepository, times(1)).deleteById(id);
    }
}
