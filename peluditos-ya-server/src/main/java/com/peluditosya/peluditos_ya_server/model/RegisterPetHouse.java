package com.peluditosya.peluditos_ya_server.model;

import jakarta.persistence.*;

@Entity
@Table(name = "casas_hogar")
public class RegisterPetHouse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre_casa_hogar", nullable = false)
    private String nombreCasaHogar;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "es_casa_hogar")
    private boolean esCasaHogar = false;

    public RegisterPetHouse() {}

    public RegisterPetHouse(String nombreCasaHogar, String email, boolean esCasaHogar) {
        this.nombreCasaHogar = nombreCasaHogar;
        this.email = email;
        this.esCasaHogar = esCasaHogar;
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public String getNombreCasaHogar() {
        return nombreCasaHogar;
    }

    public void setNombreCasaHogar(String nombreCasaHogar) {
        this.nombreCasaHogar = nombreCasaHogar;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isEsCasaHogar() {
        return esCasaHogar;
    }

    public void setEsCasaHogar(boolean esCasaHogar) {
        this.esCasaHogar = esCasaHogar;
    }
}
