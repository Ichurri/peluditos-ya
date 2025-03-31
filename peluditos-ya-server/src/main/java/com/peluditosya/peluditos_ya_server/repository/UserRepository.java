package com.peluditosya.peluditos_ya_server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.peluditosya.peluditos_ya_server.model.AppUser;

@Repository
public interface UserRepository extends JpaRepository<AppUser, Long> {
    boolean existsByEmail(String email);
    AppUser findByEmailAndPassword(String email, String password);
}
