package com.iaramartins.dto;



public record ItemPedidoResponseDTO(
    Long id,
    String nomeVela,
    int quantidade,
    Double precoUnitario,
    Double subtotal // quantidade * precoUnitario (opcional)
) {
   public ItemPedidoResponseDTO(Long id, String nomeVela,  int quantidade, Double precoUnitario) {
        this(id, nomeVela, quantidade, precoUnitario, quantidade * precoUnitario);
    }
}
