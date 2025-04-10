package com.iaramartins.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record PagamentoResponseDTO(Long id,
    Long pedidoId,
    String metodo,
    BigDecimal valor,
    String status,
    LocalDateTime dataPagamento) {
} 
