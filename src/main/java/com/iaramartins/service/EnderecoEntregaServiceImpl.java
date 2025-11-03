package com.iaramartins.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.iaramartins.dto.EnderecoEntregaRequestDTO;
import com.iaramartins.dto.EnderecoEntregaResponseDTO;
import com.iaramartins.model.EnderecoEntrega;
import com.iaramartins.model.Pedido;
import com.iaramartins.repository.EnderecoEntregaRepository;
import com.iaramartins.repository.PedidoRepository;


import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;

@ApplicationScoped
public class EnderecoEntregaServiceImpl implements EnderecoEntregaService{
     @Inject
    EnderecoEntregaRepository enderecoRepository;

    @Inject
    PedidoRepository pedidoRepository;

    @Override
    @Transactional
    public EnderecoEntregaResponseDTO criar(EnderecoEntregaRequestDTO dto) {
       Pedido pedido = pedidoRepository.findByIdOptional(dto.pedidoId())
            .orElseThrow(() -> new WebApplicationException(
                "Pedido não encontrado com ID: " + dto.pedidoId(), 
                Response.Status.BAD_REQUEST));

        EnderecoEntrega endereco = new EnderecoEntrega();
        endereco.setRua(dto.rua());
        endereco.setNumero(dto.numero());
        endereco.setComplemento(dto.complemento());
        endereco.setBairro(dto.bairro());
        endereco.setCidade(dto.cidade());
        endereco.setEstado(dto.estado());
        endereco.setCep(dto.cep());
        endereco.setPedido(pedido);

        enderecoRepository.persist(endereco);

        return toResponseDTO(endereco);
    }

    @Override
    public List<EnderecoEntregaResponseDTO> listarTodos() {
        return enderecoRepository.listAll()
                .stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public EnderecoEntregaResponseDTO buscarPorId(Long id) {
        return enderecoRepository.findByIdOptional(id)
                .map(this::toResponseDTO)
                .orElseThrow(() ->new WebApplicationException(
                "Endereço não encontrado com ID: " + id, 
                Response.Status.NOT_FOUND));
    }

    @Override
    @Transactional
    public void deletar(Long id) {
        Optional<EnderecoEntrega> optional = enderecoRepository.findByIdOptional(id);
        optional.ifPresentOrElse(enderecoRepository::delete, () -> {
            throw new IllegalArgumentException("Endereço não encontrado com ID: " + id);
        });
    }

    private EnderecoEntregaResponseDTO toResponseDTO(EnderecoEntrega endereco) {
         return EnderecoEntregaResponseDTO.valueOf(endereco);
    }
}
