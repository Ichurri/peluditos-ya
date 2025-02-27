package com.hilr.peluditos_ya_server.service;

import com.hilr.peluditos_ya_server.model.Mascota;
import com.hilr.peluditos_ya_server.repository.MascotaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MascotaService {

    private final MascotaRepository mascotaRepository;

    @Autowired
    public MascotaService(MascotaRepository mascotaRepository) {
        this.mascotaRepository = mascotaRepository;
    }

    public List<Mascota> findAllMascotas() {
        return mascotaRepository.findAll();
    }

    public Optional<Mascota> findMascotaById(Long id) {
        return mascotaRepository.findById(id);
    }

    public Mascota saveMascota(Mascota mascota) {
        return mascotaRepository.save(mascota);
    }

    public void deleteMascota(Long id) {
        mascotaRepository.deleteById(id);
    }

    public List<Mascota> findByEspecie(String especie) {
        return mascotaRepository.findByEspecie(especie);
    }

    public List<Mascota> findByEstado(String estado) {
        return mascotaRepository.findByEstado(estado);
    }

    public List<Mascota> findByEspecieAndEstado(String especie, String estado) {
        return mascotaRepository.findByEspecieAndEstado(especie, estado);
    }
}