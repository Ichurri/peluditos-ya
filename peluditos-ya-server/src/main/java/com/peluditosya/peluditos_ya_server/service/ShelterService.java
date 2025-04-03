package com.peluditosya.peluditos_ya_server.service;

import com.peluditosya.peluditos_ya_server.model.Role;
import com.peluditosya.peluditos_ya_server.model.Shelter;
import com.peluditosya.peluditos_ya_server.repository.UserRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShelterService {

    private final UserRepository userRepository;
    private final Logger logger = LoggerFactory.getLogger(ShelterService.class);

    public ShelterService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<Shelter> getAllShelters() {
        return userRepository.findSheltersByRole(Role.SHELTER);
    }

    public void deleteShelter(Long id) {
        userRepository.deleteById(id);
        logger.info("Refugio eliminado con id: {}", id);
    }
}
