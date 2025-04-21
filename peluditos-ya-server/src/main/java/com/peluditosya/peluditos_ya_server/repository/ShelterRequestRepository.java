package com.peluditosya.peluditos_ya_server.repository;

import com.peluditosya.peluditos_ya_server.model.ShelterRequest;
import com.peluditosya.peluditos_ya_server.model.ShelterRequestStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShelterRequestRepository extends JpaRepository<ShelterRequest, Long> {
    List<ShelterRequest> findByStatus(ShelterRequestStatus status);
}