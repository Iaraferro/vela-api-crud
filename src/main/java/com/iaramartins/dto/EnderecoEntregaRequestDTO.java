package com.iaramartins.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record EnderecoEntregaRequestDTO(
    @NotBlank String rua,
    @NotBlank String numero,
    String complemento,  // Opcional
    @NotBlank String bairro,
    @NotBlank String cidade,
    @NotBlank @Size(min=2, max=2) String estado,
    @NotBlank String cep,
    @NotNull Long pedidoId 
) {
} 