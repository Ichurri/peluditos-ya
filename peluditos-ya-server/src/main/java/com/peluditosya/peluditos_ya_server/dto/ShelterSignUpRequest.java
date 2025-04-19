package com.peluditosya.peluditos_ya_server.dto;

import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@NoArgsConstructor
public class ShelterSignUpRequest extends UserSignUpRequest {

    private String phone;
    private MultipartFile documentFile; // PDF file
    private String shelterName;
    private String shelterAddress;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public MultipartFile getDocumentFile() {
        return documentFile;
    }

    public void setDocumentFile(MultipartFile documentFile) {
        this.documentFile = documentFile;
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
