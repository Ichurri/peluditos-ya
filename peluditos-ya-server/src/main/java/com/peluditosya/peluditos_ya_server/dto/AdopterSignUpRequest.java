package com.peluditosya.peluditos_ya_server.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AdopterSignUpRequest extends UserSignUpRequest {
    @NotBlank(message = "El teléfono es obligatorio")
    @Pattern(regexp = "^[0-9]+$", message = "El teléfono solo debe contener números")
    private String phone;

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
