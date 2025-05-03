package com.iaramartins.dto;

import java.time.LocalDateTime;
import java.util.List;

public record PedidoResponseDTO(
    Long id,
    Long clienteId,
    LocalDateTime data,
    Double total,
    String status,
    List <ItemPedidoResponseDTO> itens
) {
    public record ItemPedidoResponseDTO(
        String nomeVela,
        int quantidade,
        Double precoUnitario
    ) {}
} 
