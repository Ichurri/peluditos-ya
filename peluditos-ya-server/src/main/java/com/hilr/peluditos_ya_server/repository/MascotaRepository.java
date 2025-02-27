package com.hilr.peluditos_ya_server.repository;

import com.hilr.peluditos_ya_server.model.Mascota;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MascotaRepository extends JpaRepository<Mascota, Long> {
    // Custom queries
    List<Mascota> findByEspecie(String especie);

    List<Mascota> findByEstado(String estado);

    List<Mascota> findByEspecieAndEstado(String especie, String estado);
}