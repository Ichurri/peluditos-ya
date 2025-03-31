package com.peluditosya.peluditos_ya_server.model;

import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
// import jakarta.persistence.DiscriminatorValue;

@Entity
@Table(name = "adopters")
@PrimaryKeyJoinColumn(name = "user_id")
public class Adopter extends AppUser {
    private String phone;
    
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}