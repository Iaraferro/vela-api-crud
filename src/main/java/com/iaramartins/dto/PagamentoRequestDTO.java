package com.iaramartins.dto;

import java.math.BigDecimal;

public record PagamentoRequestDTO(
    Long pedidoId,
    String metodo,  // "PIX", "CARTAO", etc.
    BigDecimal valor) {
} 
