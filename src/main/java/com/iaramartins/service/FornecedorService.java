package com.iaramartins.service;

import java.util.List;

import com.iaramartins.dto.FornecedorRequestDTO;
import com.iaramartins.dto.FornecedorResponseDTO;

public interface FornecedorService {
    FornecedorResponseDTO criar(FornecedorRequestDTO dto);
    List<FornecedorResponseDTO> listarTodos();
    FornecedorResponseDTO buscarPorId(Long id);
    FornecedorResponseDTO atualizar(Long id, FornecedorRequestDTO dto);
    void deletar(Long id);
    List<FornecedorResponseDTO> buscarPorNome(String nome);
}
