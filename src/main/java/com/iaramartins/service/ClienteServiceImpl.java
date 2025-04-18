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
        cliente.setNome(dto.nome());     // Isso daqui como um todo,faz com que os dados que o cliente colocou sejam inseridos no banco de dados
        cliente.setEmail(dto.email());
        cliente.setTelefone(dto.telefone());
        clienteRepository.persist(cliente);// Esse comando faz com que os dados sejam salvos no banco
        return ClienteResponseDTO.valueOf(cliente);
    }

    @Override
    public ClienteResponseDTO buscarPerfil(Long id) {
        Cliente cliente = clienteRepository.findById(id);
        if (cliente == null) {
            throw new NotFoundException("Cliente não encontrado");
        }
        return ClienteResponseDTO.valueOf(cliente);
    }

    @Override
    @Transactional
    public void atualizar(Long id, ClienteRequestDTO dto) {
        Cliente cliente = clienteRepository.findById(id);
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
        clienteRepository.delete("id",id);
    }
}
