package com.iaramartins.service;

import java.util.List;

import com.iaramartins.dto.ItemPedidoRequestDTO;
import com.iaramartins.dto.ItemPedidoResponseDTO;

public interface ItemPedidoService {
    ItemPedidoResponseDTO adicionarItem(Long pedidoId, ItemPedidoRequestDTO dto);
    void removerItem(Long itemId);
    List<ItemPedidoResponseDTO> listarItensPorPedido(Long pedidoId);
    ItemPedidoResponseDTO atualizarQuantidade(Long itemId, int novaQuantidade);
    
} 