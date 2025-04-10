package com.iaramartins.dto;

import java.util.List;

public record PedidoRequestDTO(
    Long clienteId,
    List<ItemPedidoDTO> itens
) {
    public record ItemPedidoDTO(
        Long velaId,
        int quantidade
    ) {}
} 
