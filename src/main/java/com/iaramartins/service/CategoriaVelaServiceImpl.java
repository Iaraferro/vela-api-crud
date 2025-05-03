package com.iaramartins.service;

import java.util.List;
import java.util.stream.Collectors;

import com.iaramartins.dto.CategoriaVelaRequestDTO;
import com.iaramartins.dto.CategoriaVelaResponseDTO;
import com.iaramartins.model.CategoriaVela;
import com.iaramartins.repository.CategoriaVelaRepository;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;

@ApplicationScoped
public class CategoriaVelaServiceImpl implements CategoriaVelaService{
    @Inject
    CategoriaVelaRepository repository;

    @Override
    @Transactional
    public CategoriaVelaResponseDTO criar(CategoriaVelaRequestDTO dto) {
        CategoriaVela categoria = new CategoriaVela();
        categoria.setNome(dto.nome());
        categoria.setDescricao(dto.descricao());
        
        repository.persist(categoria);
        return CategoriaVelaResponseDTO.fromEntity(categoria);
    }

    @Override
    public List<CategoriaVelaResponseDTO> listarTodos() {
        return repository.listAll().stream()
            .map(CategoriaVelaResponseDTO::fromEntity)
            .collect(Collectors.toList());
    }

    @Override
    public CategoriaVelaResponseDTO buscarPorId(Long id) {
        CategoriaVela categoria = repository.findById(id);
        if (categoria == null) {
            throw new NotFoundException("Categoria não encontrada");
        }
        return CategoriaVelaResponseDTO.fromEntity(categoria);
    }

    @Override
    @Transactional
    public void atualizar(Long id, CategoriaVelaRequestDTO dto) {
        CategoriaVela categoria = repository.findById(id);
        if (categoria != null) {
            categoria.setNome(dto.nome());
            categoria.setDescricao(dto.descricao());
        }
    }

    @Override
    @Transactional
    public void deletar(Long id) {
        repository.deleteById(id);
    }

    @Override
    public List<CategoriaVelaResponseDTO> buscarPorNome(String nome) {
    return repository.list("nome like ?1", "%" + nome + "%")
        .stream()
        .map(CategoriaVelaResponseDTO::fromEntity)
        .collect(Collectors.toList());
}
}
