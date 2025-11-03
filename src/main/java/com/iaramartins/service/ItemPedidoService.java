package com.iaramartins.service;

import java.util.List;

import com.iaramartins.dto.ItemPedidoRequestDTO;
import com.iaramartins.dto.ItemPedidoResponseDTO;


public interface ItemPedidoService {
    ItemPedidoResponseDTO adicionarItem(Long pedidoId, ItemPedidoRequestDTO dto);
    void removerItem(Long itemId);
    List<ItemPedidoResponseDTO> listarItensPorPedido(Long pedidoId);
    ItemPedidoResponseDTO atualizarQuantidade(Long itemId, int novaQuantidade);
    ItemPedidoResponseDTO buscarItemPorId(Long itemId);
    List<ItemPedidoResponseDTO> listarItensPorPedidoComFiltros(
        Long pedidoId,  
        Integer quantidadeMinima
    );

    default boolean verificarDisponibilidadeEstoque(Long velaId, int quantidade) {
        throw new UnsupportedOperationException("Método não implementado");
    }
} 