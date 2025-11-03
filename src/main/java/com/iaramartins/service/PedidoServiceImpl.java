package com.iaramartins.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.iaramartins.dto.PedidoRequestDTO;
import com.iaramartins.dto.PedidoResponseDTO;
import com.iaramartins.model.Cliente;
import com.iaramartins.model.ItemPedido;
import com.iaramartins.model.Pedido;
import com.iaramartins.model.Vela;
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
    public List<PedidoResponseDTO> listarTodos() {
    List<Pedido> pedidos = em.createQuery(
        "SELECT p FROM Pedido p", 
        Pedido.class
    ).getResultList();

    return pedidos.stream()
        .map(this::toResponseDTO)
        .collect(Collectors.toList());
}

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

        // Adiciona itens, se existirem
        List<ItemPedido> itens = new ArrayList<>();
        if (dto.itens() != null) {
            for (PedidoRequestDTO.ItemPedidoRequestDTO itemDTO : dto.itens()) {
                Vela vela = velaRepository.findById(itemDTO.velaId());
                if (vela == null) {
                    throw new NotFoundException("Vela com ID " + itemDTO.velaId() + " não encontrada");
                }

                // Verifica se há estoque suficiente
        if (vela.getEstoque() < itemDTO.quantidade()) {
            throw new IllegalStateException("Estoque insuficiente para a vela: " + vela.getNome());
        }

                 // Diminui o estoque
                vela.setEstoque(vela.getEstoque() - itemDTO.quantidade());
                ItemPedido item = new ItemPedido();
                item.setVela(vela);
                item.setQuantidade(itemDTO.quantidade());
                item.setPrecoUnitario(vela.getPreco());
                item.setPedido(pedido);
                itens.add(item);
            }
        }
        pedido.setItens(itens);

        // Calcula o total
        pedido.calcularTotal();
        
        
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
            pedido.getStatus(),
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

    @Override
    @Transactional
    public void atualizarStatus(Long id, String status) {
    Pedido pedido = em.find(Pedido.class, id);
    if (pedido == null) {
        throw new NotFoundException("Pedido não encontrado");
    }
    
    // Valida transições de status (ex: não pode voltar de CANCELADO para PENDENTE)
    if (pedido.getStatus().equals("CANCELADO") && !status.equals("CANCELADO")) {
        throw new IllegalStateException("Pedido cancelado não pode ter status alterado");
    }
    
    pedido.setStatus(status);
    }

    @Override
    @Transactional
    public void cancelarPedido(Long id) {
    Pedido pedido = em.find(Pedido.class, id);
    if (pedido == null) {
        throw new NotFoundException("Pedido não encontrado");
    }
    
    // Valida se pode ser cancelado (ex: não cancelar pedido já enviado)
    if (pedido.getStatus().equals("ENVIADO") || pedido.getStatus().equals("ENTREGUE")) {
        throw new IllegalStateException("Pedido já enviado não pode ser cancelado");
    }
    
    pedido.setStatus("CANCELADO");
    
    // Se houver pagamento, marca como estornado
    if (pedido.getPagamento() != null) {
        pedido.getPagamento().setStatus("ESTORNADO");
    }
    }

    

}
