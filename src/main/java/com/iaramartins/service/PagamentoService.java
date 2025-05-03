package com.iaramartins.service;

import java.util.List;

import com.iaramartins.dto.PagamentoRequestDTO;
import com.iaramartins.dto.PagamentoResponseDTO;

public interface PagamentoService {
    PagamentoResponseDTO criar(PagamentoRequestDTO dto);
    PagamentoResponseDTO buscarPorId(Long id);
    PagamentoResponseDTO buscarPorPedidoId(Long pedidoId);
    void atualizarStatus(Long id, String novoStatus);
    List<PagamentoResponseDTO> listarTodos(String status);
    void deletarPagamento(Long id);
}
