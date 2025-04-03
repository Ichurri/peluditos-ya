package com.peluditosya.peluditos_ya_server.model;

import com.peluditosya.peluditos_ya_server.dto.Role;
import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;

@Entity
@Table(name = "shelters")
@PrimaryKeyJoinColumn(name = "user_id")
public class Shelter extends AppUser {

    private String documentNumber;
    private String shelterName;
    private String shelterAddress;
    private String phone;

    public Shelter() {
        super();
        this.setRole(Role.SHELTER); // Se asegura que el rol no sea null
    }

    public String getDocumentNumber() {
        return documentNumber;
    }
    public void setDocumentNumber(String documentNumber) {
        this.documentNumber = documentNumber;
    }

    public String getShelterName() {
        return shelterName;
    }
    public void setShelterName(String shelterName) {
        this.shelterName = shelterName;
    }

    public String getShelterAddress() {
        return shelterAddress;
    }
    public void setShelterAddress(String shelterAddress) {
        this.shelterAddress = shelterAddress;
    }

    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
}
