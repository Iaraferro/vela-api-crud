package com.iaramartins.dto;

import jakarta.validation.constraints.NotBlank;

public record IngredienteRequestDTO(
   @NotBlank String recipiente,
   @NotBlank String pavio,
   @NotBlank String tipoCera
) {
} 
