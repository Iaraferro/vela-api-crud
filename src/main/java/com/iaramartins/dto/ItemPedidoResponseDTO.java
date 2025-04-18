package com.iaramartins.dto;

import com.iaramartins.model.TipoVela;

public record ItemPedidoResponseDTO(
    Long id,
    String nomeVela,
    TipoVela tipoVela,
    int quantidade,
    Double precoUnitario,
    Double subtotal // quantidade * precoUnitario (opcional)
) {
   public ItemPedidoResponseDTO(Long id, String nomeVela,TipoVela tipoVela,  int quantidade, Double precoUnitario) {
        this(id, nomeVela, tipoVela,quantidade, precoUnitario, quantidade * precoUnitario);
    }
}
