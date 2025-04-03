package com.peluditosya.peluditos_ya_server.dto;

public class AdopterSignUpRequest extends UserSignUpRequest {
    private String phone;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
