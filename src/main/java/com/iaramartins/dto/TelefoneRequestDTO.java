package com.iaramartins.dto;

import io.smallrye.common.constraint.NotNull;
import jakarta.validation.constraints.NotBlank;

public record TelefoneRequestDTO(
    @NotBlank String numero,
    @NotBlank String codigoArea,
    @NotNull Long pessoaId 
) {
    
}
