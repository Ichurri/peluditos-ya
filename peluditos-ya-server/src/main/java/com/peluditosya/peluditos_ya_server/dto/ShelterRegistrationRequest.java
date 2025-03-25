package com.peluditosya.peluditos_ya_server.dto;

import org.springframework.web.multipart.MultipartFile;

public class ShelterRegistrationRequest {
    private String legalName;
    private String address;
    private MultipartFile legalIdDocument;
    private MultipartFile addressProofDocument;

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

    public MultipartFile getLegalIdDocument() {
        return legalIdDocument;
    }

    public void setLegalIdDocument(MultipartFile legalIdDocument) {
        this.legalIdDocument = legalIdDocument;
    }

    public MultipartFile getAddressProofDocument() {
        return addressProofDocument;
    }

    public void setAddressProofDocument(MultipartFile addressProofDocument) {
        this.addressProofDocument = addressProofDocument;
    }
}