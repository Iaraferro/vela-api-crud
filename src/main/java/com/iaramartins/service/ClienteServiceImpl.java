package com.iaramartins.service;


import com.iaramartins.dto.ClienteRequestDTO;
import com.iaramartins.dto.ClienteResponseDTO;
import com.iaramartins.model.Cliente;
import com.iaramartins.repository.ClienteRepository;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;


@ApplicationScoped
public class ClienteServiceImpl implements ClienteService {
    @Inject
    ClienteRepository clienteRepository;

    @Override
    @Transactional
    public ClienteResponseDTO cadastrar(ClienteRequestDTO dto){
        Cliente cliente = new Cliente();
        cliente.nome = dto.nome();
        cliente.email = dto.email();
        cliente.telefone = dto.telefone();
        clienteRepository.persist(cliente);
        return ClienteResponseDTO.valueOf(cliente);
    }

    @Override
    public ClienteResponseDTO getById(Long id) {
        Cliente cliente = clienteRepository.findById(id);
        if (cliente == null) {
            throw new NotFoundException("Cliente não encontrado");
        }
        return ClienteResponseDTO.valueOf(cliente);
    }

    @Override
    @Transactional
    public void update(Long id, ClienteRequestDTO dto) {
        Cliente cliente = clienteRepository.findById(id);
        if (cliente == null) {
            throw new NotFoundException("Cliente não encontrado");
        }
        cliente.nome = dto.nome();
        cliente.email = dto.email();
        cliente.telefone = dto.telefone();
    }

    
    @Override
    @Transactional
    public void deletarCliente(Long id) {
        clienteRepository.delete("id",id);
    }
}
