package com.peluditosya.peluditos_ya_server.model;

import jakarta.persistence.Entity;
import jakarta.persistence.DiscriminatorValue;

@Entity
@DiscriminatorValue("ADOPTER")
public class Adopter extends AppUser {
    private String phone;
    
    // Getters y Setters
}