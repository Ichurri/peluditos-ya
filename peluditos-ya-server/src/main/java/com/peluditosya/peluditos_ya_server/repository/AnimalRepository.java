package com.peluditosya.peluditos_ya_server.repository;

import com.peluditosya.peluditos_ya_server.model.Animal;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnimalRepository extends JpaRepository<Animal, Long> {
    // Additional query methods if needed
}
