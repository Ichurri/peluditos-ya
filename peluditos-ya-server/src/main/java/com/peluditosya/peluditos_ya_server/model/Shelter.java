package com.peluditosya.peluditos_ya_server.model;

import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;

@Entity
@Table(name = "shelters")
@PrimaryKeyJoinColumn(name = "user_id")
public class Shelter extends AppUser {

    private String documentPath; // Path to PDF file
    private String shelterName;
    private String shelterAddress;
    private String phone;

    public Shelter() {
        super();
        this.setRole(Role.SHELTER);
    }

    public String getDocumentPath() {
        return documentPath;
    }

    public void setDocumentPath(String documentPath) {
        this.documentPath = documentPath;
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
