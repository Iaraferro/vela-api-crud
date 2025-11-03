package com.iaramartins.service;

import java.util.List;
import java.util.stream.Collectors;

import com.iaramartins.dto.FornecedorRequestDTO;
import com.iaramartins.dto.FornecedorResponseDTO;
import com.iaramartins.model.Fornecedor;
import com.iaramartins.repository.FornecedorRepository;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class FornecedorServiceImpl implements FornecedorService {
    @Inject
    EntityManager em;

    @Inject
    FornecedorRepository repository;

    @Override
    @Transactional
    public FornecedorResponseDTO criar(FornecedorRequestDTO dto) {
        Fornecedor fornecedor = new Fornecedor();
        fornecedor.setNome(dto.nome());
        fornecedor.setCnpj(dto.cnpj());
        
        repository.persist(fornecedor);
        return FornecedorResponseDTO.fromEntity(fornecedor);
    }

    @Override
    public List<FornecedorResponseDTO> listarTodos() {
        return repository.listAll().stream()
            .map(FornecedorResponseDTO::fromEntity)
            .collect(Collectors.toList());
    }

    @Override
    public FornecedorResponseDTO buscarPorId(Long id) {
        Fornecedor fornecedor = repository.findById(id);
        return fornecedor != null ? FornecedorResponseDTO.fromEntity(fornecedor) : null;
    }

    @Override
    @Transactional
    public FornecedorResponseDTO atualizar(Long id, FornecedorRequestDTO dto) {
        Fornecedor fornecedor = repository.findById(id);
        if (fornecedor != null) {
            fornecedor.setCnpj(dto.cnpj());
            repository.persist(fornecedor);
        }
        return FornecedorResponseDTO.fromEntity(fornecedor);
    }

    @Override
    @Transactional
    public void deletar(Long id) {
        repository.delete(id);
    }

    @Override
public List<FornecedorResponseDTO> buscarPorNome(String nome) {
    return em.createQuery("SELECT f FROM Fornecedor f WHERE f.nome LIKE :nome", Fornecedor.class)
        .setParameter("nome", "%" + nome + "%")
        .getResultList()
        .stream()
        .map(FornecedorResponseDTO::fromEntity)
        .collect(Collectors.toList());
    }
}
