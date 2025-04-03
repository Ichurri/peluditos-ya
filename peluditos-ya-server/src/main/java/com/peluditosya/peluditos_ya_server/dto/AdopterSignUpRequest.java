package com.peluditosya.peluditos_ya_server.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AdopterSignUpRequest extends UserSignUpRequest {
    private String phone;

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
