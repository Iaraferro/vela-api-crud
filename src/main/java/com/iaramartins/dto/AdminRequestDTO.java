package com.iaramartins.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record AdminRequestDTO(
    @NotBlank @Email 
    String email,
    @NotBlank @Size(min=8)
    String senha,
    @NotBlank String departamento
) {


}
