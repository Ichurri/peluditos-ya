package com.peluditosya.peluditos_ya_server.dto;

public class ShelterSignUpRequest {
    private String name;
    private String email;
    private String password;
    private String city;
    private String phone;
    private String documentNumber;
    private String shelterName;
    private String shelterAddress;

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    
    public String getCity() {
        return city;
    }
    public void setCity(String city) {
        this.city = city;
    }
    
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
