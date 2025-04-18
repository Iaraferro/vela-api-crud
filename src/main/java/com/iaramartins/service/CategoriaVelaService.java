package com.iaramartins.service;

import java.util.List;

import com.iaramartins.dto.CategoriaVelaRequestDTO;
import com.iaramartins.dto.CategoriaVelaResponseDTO;

public interface CategoriaVelaService {
    CategoriaVelaResponseDTO criar(CategoriaVelaRequestDTO dto);
    List<CategoriaVelaResponseDTO> listarTodos();
    CategoriaVelaResponseDTO buscarPorId(Long id);
    void atualizar(Long id, CategoriaVelaRequestDTO dto);
    void deletar(Long id);
}
