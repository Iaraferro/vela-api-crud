package com.iaramartins.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;



public record PagamentoRequestDTO(
    @NotNull Long pedidoId,
    @NotBlank String metodo,  // "PIX", "CARTAO", etc.
    @Positive BigDecimal valor) {
} 
