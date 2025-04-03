package com.peluditosya.peluditos_ya_server.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ShelterSignUpRequest extends UserSignUpRequest {

    @NotBlank(message = "El teléfono es obligatorio")
    @Pattern(regexp = "^[0-9]+$", message = "El teléfono solo debe contener números")
    private String phone;

    @NotBlank(message = "El número de documento es obligatorio")
    @Size(min = 4, max = 20, message = "El número de documento debe tener entre 4 y 20 caracteres")
    private String documentNumber;

    @NotBlank(message = "El nombre del refugio es obligatorio")
    private String shelterName;

    @NotBlank(message = "La dirección del refugio es obligatoria")
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
