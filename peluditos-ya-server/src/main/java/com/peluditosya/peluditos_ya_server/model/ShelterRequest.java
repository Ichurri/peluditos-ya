package com.peluditosya.peluditos_ya_server.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "shelter_requests")
public class ShelterRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private AppUser user;

    @Enumerated(EnumType.STRING)
    private ShelterRequestStatus status;

    @Column(name = "latitude")
    private Double latitude;

    @Column(name = "longitude")
    private Double longitude;


    private String description;

    private LocalDateTime createdAt = LocalDateTime.now();

    private String phone;
    private String shelterAddress;
    private String shelterName;

    public long getShelterId(){
        return id;
    }
}
