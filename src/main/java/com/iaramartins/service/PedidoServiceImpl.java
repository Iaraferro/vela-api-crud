package com.iaramartins.service;

import java.util.List;
import java.util.stream.Collectors;

import com.iaramartins.dto.PedidoRequestDTO;
import com.iaramartins.dto.PedidoResponseDTO;
import com.iaramartins.model.Cliente;
import com.iaramartins.model.ItemPedido;
import com.iaramartins.model.Pedido;
import com.iaramartins.model.Vela;
import com.iaramartins.repository.ClienteRepository;
import com.iaramartins.repository.PedidoRepository;
import com.iaramartins.repository.VelaRepository;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;

@ApplicationScoped
public class PedidoServiceImpl implements PedidoService {
    @Inject
    PedidoRepository pedidoRepository;

    @Inject
    ClienteRepository clienteRepository;

    @Inject
    VelaRepository velaRepository;

    @Override
    @Transactional
    public PedidoResponseDTO criar(PedidoRequestDTO dto) {
        //Valida cliente
        Cliente cliente = clienteRepository.findById(dto.clienteId());
        if (cliente == null) {
            throw new NotFoundException("Cliente não encontrado");
        }

        // Cria o pedido (sem itens ainda)
        Pedido pedido = new Pedido();
        pedido.cliente = cliente;
        pedidoRepository.persist(pedido); // Persiste primeiro para gerar ID

        //Adiciona os itens
        pedido.itens = dto.itens().stream()
            .map(itemDto -> {
                Vela vela = velaRepository.findById(itemDto.velaId());
                if (vela == null) {
                    throw new NotFoundException("Vela não encontrada: ID " + itemDto.velaId());
                }

                ItemPedido item = new ItemPedido();
                item.pedido = pedido; // Associa ao pedido
                item.vela = vela;
                item.quantidade = itemDto.quantidade();
                item.precoUnitario = vela.preco; // Guarda o preço no momento da compra
                return item;
            })
            .collect(Collectors.toList());

        //Calcula o total
        pedido.calcularTotal(); // Usa o método auxiliar do modelo

        // Retorna o DTO de resposta
        return toResponseDTO(pedido);
    }

    // Conversor de Pedido para DTO
    private PedidoResponseDTO toResponseDTO(Pedido pedido) {
        return new PedidoResponseDTO(
            pedido.id,
            pedido.cliente.id,
            pedido.data,
            pedido.total,
            pedido.itens.stream()
                .map(item -> new PedidoResponseDTO.ItemPedidoResponseDTO(
                    item.vela.nome,
                    item.quantidade,
                    item.precoUnitario
                ))
                .collect(Collectors.toList())
        );
    }

    @Override
    public PedidoResponseDTO getById(Long id) {
        Pedido pedido = pedidoRepository.findById(id);
        if (pedido == null) {
            throw new NotFoundException("Pedido não encontrado");
        }
        return toResponseDTO(pedido);
    }

    @Override
    public List<PedidoResponseDTO> listarPorCliente(Long clienteId) {
        return pedidoRepository.list("cliente.id", clienteId).stream()
            .map(this::toResponseDTO)
            .collect(Collectors.toList());
    }

}
