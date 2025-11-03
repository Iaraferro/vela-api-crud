package com.iaramartins.dto;

import jakarta.validation.constraints.NotBlank;

public record PessoaFisicaRequestDTO(
    @NotBlank String nome,
    @NotBlank String cpf
) {
    
}
