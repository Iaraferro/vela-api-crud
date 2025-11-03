package com.iaramartins.dto;

import jakarta.validation.constraints.NotBlank;

public record IngredienteResponse(
    @NotBlank String nome,
    @NotBlank String cpf
) {
    
}
