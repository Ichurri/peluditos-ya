package com.peluditosya.peluditos_ya_server.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class ShelterRequestDto {
    private Long userId;
    private String description;
    private String phone;
    private String shelterAddress;
    private String shelterName;
}
