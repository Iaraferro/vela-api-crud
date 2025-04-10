package com.iaramartins.service;

import java.util.List;

import com.iaramartins.dto.VelaRequestDTO;
import com.iaramartins.dto.VelaResponseDTO;
import com.iaramartins.model.Vela;
import com.iaramartins.repository.VelaRepository;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;

@ApplicationScoped
public class VelaServiceImpl implements VelaService {
    @Inject
    VelaRepository velaRepository; // O service usa o repository

    
    // CADASTRAR (mínimo de validação)
    @Override
    @Transactional
    public VelaResponseDTO cadastrar(VelaRequestDTO dto) {
        Vela vela = new Vela();
        vela.nome = dto.nome();
        vela.tipo = dto.tipo();
        vela.aroma = dto.aroma();
        vela.preco = dto.preco();
        vela.ingrediente = dto.ingredientes();
        vela.ritualAssociado = dto.ritualAssociado();
        
        velaRepository.persist(vela);
        return VelaResponseDTO.fromEntity(vela);
    }
    // BUSCAR POR ID
    @Override
    public VelaResponseDTO getById(Long id) {
        Vela vela = velaRepository.findById(id);
        if (vela == null) {
            throw new NotFoundException("Vela não encontrada");
        }
        return VelaResponseDTO.fromEntity(vela);  // Convertendo para DTO
    }

    // LISTAR DISPONÍVEIS
    @Override
    public List<VelaResponseDTO> listarDisponiveis() {
        return velaRepository.list("disponivel", true)
            .stream()
            .map(VelaResponseDTO::fromEntity)  // Convertendo cada vela para DTO
            .toList();
    }


    // Atualizar vela
    @Override
    @Transactional
    public void update(Long id, VelaRequestDTO dto) {
        Vela vela = velaRepository.findById(id);
        if (vela == null){
            throw new NotFoundException("Vela não encontrada");
        } 
        vela.nome = dto.nome();
        vela.preco = dto.preco();
        vela.tipo = dto.tipo(); // Novo campo
    }

   // DELETAR VELA
   @Override
   @Transactional
   public void deletarVela(Long id) {
       velaRepository.deleteById(id);
   }
}
