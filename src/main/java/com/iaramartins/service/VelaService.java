package com.iaramartins.service;



import java.util.List;

import com.iaramartins.dto.VelaRequestDTO;
import com.iaramartins.dto.VelaResponseDTO;


public interface VelaService {
  VelaResponseDTO cadastrar(VelaRequestDTO dto);
  void update(Long id, VelaRequestDTO dto);
  List<VelaResponseDTO> listarDisponiveis();
  VelaResponseDTO getById(Long id);
  void deletarVela(Long id);
  List<VelaResponseDTO> listarOrdenadasPorPreco();

}
