package com.iaramartins.service;

import java.util.List;

import com.iaramartins.dto.VelaRequestDTO;
import com.iaramartins.dto.VelaResponseDTO;
import com.iaramartins.model.Vela;
import com.iaramartins.repository.VelaRepository;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;

@ApplicationScoped
public class VelaServiceImpl implements VelaService {
    @Inject
    VelaRepository velaRepository; // O service usa o repository

    @Inject
    EntityManager em;
    
    // CADASTRAR (mínimo de validação)
    @Override
    @Transactional
    public VelaResponseDTO cadastrar(VelaRequestDTO dto) {
        Vela vela = new Vela();
        vela.setNome(dto.nome());
        vela.setTipo(dto.tipo());
        vela.setAroma(dto.aroma());
        vela.setPreco(dto.preco());
        vela.setIngrediente(dto.ingrediente());
        vela.setRitualAssociado(dto.ritualAssociado());
        
        em.persist(vela);
        return VelaResponseDTO.fromEntity(vela);
    }
    // BUSCAR POR ID
    @Override
    public VelaResponseDTO getById(Long id) {
        Vela vela = em.find(Vela.class, id); // Substitui velaRepository.findById()
        if (vela == null) {
            throw new NotFoundException("Vela não encontrada");
        }
        return VelaResponseDTO.fromEntity(vela);  // Convertendo para DTO
    }

    // LISTAR DISPONÍVEIS
    @Override
    public List<VelaResponseDTO> listarDisponiveis() {
        return em.createQuery("SELECT v FROM Vela v WHERE v.disponivel = true", Vela.class)
            .getResultList()
            .stream()
            .map(VelaResponseDTO::fromEntity)  // Convertendo cada vela para DTO
            .toList();
    }


    // Atualizar vela
    @Override
    @Transactional
    public void update(Long id, VelaRequestDTO dto) {
        Vela vela = em.find(Vela.class, id);
        if (vela == null){
            throw new NotFoundException("Vela não encontrada");
        } 
        vela.setNome(dto.nome());
        vela.setPreco(dto.preco());
        vela.setTipo(dto.tipo()); // Novo campo
    }

   // DELETAR VELA
   @Override
   @Transactional
   public void deletarVela(Long id) {
    Vela vela = em.find(Vela.class, id);
    if (vela != null) {
        em.remove(vela); // Remove a entidade gerenciada
    }
   }

   @Override
   public List<VelaResponseDTO> listarOrdenadasPorPreco() {
   return em.createQuery("SELECT v FROM Vela v WHERE v.disponivel = true ORDER BY v.preco ASC", Vela.class)
            .getResultList()
            .stream()
            .map(VelaResponseDTO::fromEntity)
            .toList();
   }
}
