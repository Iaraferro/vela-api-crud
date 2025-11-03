package com.iaramartins.service;

import java.util.List;

import com.iaramartins.dto.PedidoRequestDTO;
import com.iaramartins.dto.PedidoResponseDTO;

public interface PedidoService {
    PedidoResponseDTO criar(PedidoRequestDTO dto); // String username
    PedidoResponseDTO getById(Long id);
    List<PedidoResponseDTO>listarPorCliente(Long clienteId);
    List<PedidoResponseDTO> listarTodos();
    void atualizarStatus(Long id, String status);
    void cancelarPedido(Long id);
} 
