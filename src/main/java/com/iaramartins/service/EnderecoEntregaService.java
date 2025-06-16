package com.iaramartins.service;

import com.iaramartins.dto.EnderecoEntregaRequestDTO;
import com.iaramartins.dto.EnderecoEntregaResponseDTO;

import java.util.List;

public interface EnderecoEntregaService {
  EnderecoEntregaResponseDTO criar(EnderecoEntregaRequestDTO dto);
  EnderecoEntregaResponseDTO buscarPorId(Long id);
  List<EnderecoEntregaResponseDTO> listarTodos();
  void deletar(Long id);
    
} 
