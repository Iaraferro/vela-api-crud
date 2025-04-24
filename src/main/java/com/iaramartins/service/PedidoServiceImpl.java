package com.iaramartins.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import com.iaramartins.dto.PedidoRequestDTO;
import com.iaramartins.dto.PedidoResponseDTO;
import com.iaramartins.model.Cliente;
import com.iaramartins.model.Pedido;
import com.iaramartins.repository.ClienteRepository;
import com.iaramartins.repository.VelaRepository;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;

@ApplicationScoped
public class PedidoServiceImpl implements PedidoService {

    @Inject
    EntityManager em; //Substitui o PedidoRepository

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
        pedido.setCliente(cliente);
        pedido.setData(LocalDateTime.now());
        pedido.setStatus("PENDENTE"); // Status inicial
        pedido.setTotal(0.0); // Total zerado (itens serão adicionados depois)
        
        em.persist(pedido); // Persiste primeiro para gerar ID
        // Retorna o DTO de resposta
        return toResponseDTO(pedido);
    }
   
    // Conversor de Pedido para DTO (ajustado para lista vazia se itens forem nulos)
    private PedidoResponseDTO toResponseDTO(Pedido pedido) {
        List<PedidoResponseDTO.ItemPedidoResponseDTO> itens = pedido.getItens() != null ?
            pedido.getItens().stream()
                .map(item -> new PedidoResponseDTO.ItemPedidoResponseDTO(
                    item.getVela().getNome(),
                    item.getQuantidade(),
                    item.getPrecoUnitario()
                ))
                .collect(Collectors.toList()) :
            List.of(); // Retorna lista vazia se itens forem nulos

        return new PedidoResponseDTO(
            pedido.getId(),
            pedido.getCliente().getId(),
            pedido.getData(),
            pedido.getTotal(),
            itens
        );
    }
    @Override
    public PedidoResponseDTO getById(Long id) {
        Pedido pedido = em.find(Pedido.class, id);
        if (pedido == null) {
            throw new NotFoundException("Pedido não encontrado");
        }
        return toResponseDTO(pedido);
    }

    @Override
    public List<PedidoResponseDTO> listarPorCliente(Long clienteId) {
        // Consulta JPQL equivalente ao list("cliente.id", clienteId) do Panache
        List<Pedido> pedidos = em.createQuery(
            "SELECT p FROM Pedido p WHERE p.cliente.id = :clienteId", 
            Pedido.class
        )
        .setParameter("clienteId", clienteId)
        .getResultList();

        return pedidos.stream()
            .map(this::toResponseDTO)
            .collect(Collectors.toList());
    }

}
