package com.peluditosya.peluditos_ya_server.repository;

import com.peluditosya.peluditos_ya_server.model.AppUser;
import com.peluditosya.peluditos_ya_server.model.Shelter;
import com.peluditosya.peluditos_ya_server.dto.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface UserRepository extends JpaRepository<AppUser, Long> {

    // Método para autenticación (login)
    AppUser findByEmailAndPassword(String email, String password);

    // Método para verificar si un email ya está registrado
    boolean existsByEmail(String email);

    // Método para obtener todos los refugios (Shelter) por rol
    @Query("SELECT s FROM shelter ")
    List<Shelter> findByRole(@Param("role") Role role);
}