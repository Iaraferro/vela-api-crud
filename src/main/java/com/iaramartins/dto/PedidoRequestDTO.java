package com.iaramartins.dto;


import java.util.List;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record PedidoRequestDTO(
    @NotNull(message = "ID do cliente é obrigatório")
    Long clienteId,
    @NotEmpty(message = "O pedido deve conter pelo menos um item")
    List<ItemPedidoRequestDTO> itens
   
) {
    public record ItemPedidoRequestDTO(
        @NotNull(message = "ID da vela é obrigatório")
        Long velaId,
        @Positive(message = "Quantidade é obrigatória")
        Integer quantidade
    ) {}
} 
