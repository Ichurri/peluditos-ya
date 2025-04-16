package com.peluditosya.peluditos_ya_server.dto;

public class AdopterSignUpRequest extends UserSignUpRequest {

    private String phone;

    public String getPhone(){
        return this.phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
