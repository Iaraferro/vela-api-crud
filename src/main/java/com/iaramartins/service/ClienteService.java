package com.iaramartins.service;

import java.util.List;

import com.iaramartins.dto.ClienteRequestDTO;
import com.iaramartins.dto.ClienteResponseDTO;

public interface ClienteService {  
 ClienteResponseDTO cadastrar(ClienteRequestDTO dto);
 ClienteResponseDTO buscarPerfil(Long id);
 void atualizar(Long id, ClienteRequestDTO dto); 
 void deletarCliente(Long id);
 List<ClienteResponseDTO> listarTodos();
 List<ClienteResponseDTO> buscarPorNome(String nome);
} 
