package com.iaramartins.service;

import java.util.List;
import java.util.stream.Collectors;

import com.iaramartins.dto.ClienteResponseDTO;
import com.iaramartins.dto.VelaRequestDTO;
import com.iaramartins.dto.VelaResponseDTO;
import com.iaramartins.model.Cliente;
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
        vela.setPreco(dto.preco());
        vela.setRitualAssociado(dto.ritualAssociado());
        vela.setEstoque(dto.estoque());
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

    @Override
    public List<VelaResponseDTO> listarTodas() {
        return em.createQuery("SELECT v FROM Vela v", Vela.class)
            .getResultList()
            .stream()
            .map(VelaResponseDTO::fromEntity)
            .toList();
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

   @Override
   public Integer verificarEstoque(Long id) {
    Vela vela = em.find(Vela.class, id);
    if (vela == null) {
        throw new NotFoundException("Vela não encontrada");
    }
    return vela.getEstoque();
    }


    @Override
public List<VelaResponseDTO> getAll(int page, int pageSize) {
    try {
        List<Vela> list = em.createQuery("SELECT v FROM Vela v ORDER BY v.id", Vela.class)
                           .setFirstResult(page * pageSize)
                           .setMaxResults(pageSize)
                           .getResultList();
        
        return list.stream()
                  .map(VelaResponseDTO::fromEntity)  // Use o método correto
                  .collect(Collectors.toList());
    } catch (Exception e) {
        throw new RuntimeException("Erro ao buscar velas paginadas: " + e.getMessage(), e);
    }
}
    @Override
    public long count(){
        return velaRepository.count();
    }
}
