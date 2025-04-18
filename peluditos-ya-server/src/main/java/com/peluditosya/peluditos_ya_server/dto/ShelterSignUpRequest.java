package com.peluditosya.peluditos_ya_server.dto;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class ShelterSignUpRequest extends UserSignUpRequest {

    private String phone;

    private String documentNumber;

    private String shelterName;

    private String shelterAddress;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
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
}
