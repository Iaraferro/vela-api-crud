package com.iaramartins.dto;

import jakarta.validation.constraints.NotBlank;

public record AromaRequestDTO(
    @NotBlank
    String essenciaAromatica) {

} 
