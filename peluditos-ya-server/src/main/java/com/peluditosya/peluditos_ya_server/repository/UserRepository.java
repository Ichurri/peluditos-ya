package com.peluditosya.peluditos_ya_server.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.peluditosya.peluditos_ya_server.model.AppUser;
import com.peluditosya.peluditos_ya_server.model.Shelter;

public interface UserRepository extends JpaRepository<AppUser, Long> {
    boolean existsByEmail(String email);
    AppUser findByEmailAndPassword(String email, String password);

    @Query("SELECT s FROM Shelter s WHERE s.role = 'REFUGIO'")
    List<Shelter> findAllShelters();
}