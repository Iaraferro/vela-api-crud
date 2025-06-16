package com.iaramartins.service;


import java.util.List;
import java.util.stream.Collectors;

import com.iaramartins.dto.ClienteRequestDTO;
import com.iaramartins.dto.ClienteResponseDTO;
import com.iaramartins.model.Cliente;
import com.iaramartins.model.Role;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;


@ApplicationScoped
public class ClienteServiceImpl implements ClienteService {
    @Inject
    EntityManager em;

    @Override
    @Transactional
    public ClienteResponseDTO cadastrar(ClienteRequestDTO dto){
    Cliente cliente = new Cliente();
    cliente.setNome(dto.nome());
    cliente.setEmail(dto.email());
    cliente.setTelefone(dto.telefone());
    cliente.setSenha(dto.senha());
    cliente.setRole(Role.valueOf(dto.role().toUpperCase()));
    cliente.setAtivo(true);
    em.persist(cliente);
    em.flush();
    return new ClienteResponseDTO(
        cliente.getId(),
        cliente.getNome(),
        cliente.getEmail(),
        cliente.getTelefone()
        );
    }

    @Override
    public ClienteResponseDTO buscarPerfil(Long id) {
        Cliente cliente = em.find(Cliente.class, id);
        if (cliente == null) {
            throw new NotFoundException("Cliente não encontrado");
        }
        return ClienteResponseDTO.valueOf(cliente);
    }

    @Override
    @Transactional
    public void atualizar(Long id, ClienteRequestDTO dto) {
        Cliente cliente = em.find(Cliente.class, id);
        if (cliente == null) {
            throw new NotFoundException("Cliente não encontrado");
        }
        cliente.setNome(dto.nome());
        cliente.setEmail(dto.email());
        cliente.setTelefone(dto.telefone());
    }

    
    @Override
    @Transactional
    public void deletarCliente(Long id) {
        Cliente cliente = em.find(Cliente.class, id);
        if (cliente != null) {
            em.remove(cliente); // Os pedidos serão deletados automaticamente
        }
    }

    @Override
    public List<ClienteResponseDTO> listarTodos() {
    return em.createQuery("SELECT c FROM Cliente c WHERE c.ativo = true", Cliente.class)
        .getResultList()
        .stream()
        .map(ClienteResponseDTO::valueOf)
        .collect(Collectors.toList());
   }

    @Override
    public List<ClienteResponseDTO> buscarPorNome(String nome) {
    return em.createQuery("SELECT c FROM Cliente c WHERE c.ativo = true AND c.nome LIKE :nome", Cliente.class)
        .setParameter("nome", "%" + nome + "%")
        .getResultList()
        .stream()
        .map(ClienteResponseDTO::valueOf)
        .collect(Collectors.toList());
    }
}
