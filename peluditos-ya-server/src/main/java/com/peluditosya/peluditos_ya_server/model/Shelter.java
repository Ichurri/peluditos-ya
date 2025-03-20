package com.peluditosya.peluditos_ya_server.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "shelters")
@PrimaryKeyJoinColumn(name = "user_id")
public class Shelter extends AppUser {
    private String legalName;
    private String address;
    
    @OneToMany(mappedBy = "shelter", cascade = CascadeType.ALL)
    private List<ShelterDocument> documents;
    
    public String getLegalName() {
        return legalName;
    }

    public void setLegalName(String legalName) {
        this.legalName = legalName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<ShelterDocument> getDocuments() {
        return documents;
    }

    public void setDocuments(List<ShelterDocument> documents) {
        this.documents = documents;
    }
}