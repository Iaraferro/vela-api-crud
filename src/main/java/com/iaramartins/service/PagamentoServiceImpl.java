package com.iaramartins.service;


import java.time.LocalDateTime;
import java.util.List;

import com.iaramartins.dto.PagamentoRequestDTO;
import com.iaramartins.dto.PagamentoResponseDTO;
import com.iaramartins.model.Pagamento;
import com.iaramartins.model.Pedido;
import com.iaramartins.repository.PedidoRepository;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;

@ApplicationScoped
public class PagamentoServiceImpl implements PagamentoService{
     @Inject
    EntityManager em;

    @Inject
    PedidoRepository pedidoRepository;

    @Override
    @Transactional
    public PagamentoResponseDTO criar(PagamentoRequestDTO dto) {
        //Valida se o pedido existe
        Pedido pedido = pedidoRepository.findById(dto.pedidoId());
        if (pedido == null) {
            throw new NotFoundException("Pedido não encontrado: " + dto.pedidoId());
        }

        // Valida se o pedido já tem pagamento
        if (pedido.getPagamento() != null) {
            throw new IllegalStateException("Este pedido já possui um pagamento registrado");
        }

        //Cria o pagamento
        Pagamento pagamento = new Pagamento();
        pagamento.setPedido(pedido);
        pagamento.setMetodo(dto.metodo());
        pagamento.setValor(dto.valor());
        pagamento.setStatus("PENDENTE");
        pagamento.setDataPagamento(LocalDateTime.now());
        
        em.persist(pagamento);
        pedido.setPagamento(pagamento);
        // Retorna o DTO de resposta
        return toResponseDTO(pagamento);
    }

    @Override
    public PagamentoResponseDTO buscarPorId(Long id) {
        Pagamento pagamento = em.find(Pagamento.class, id);
        if (pagamento == null) {
            throw new NotFoundException("Pagamento não encontrado com ID: " + id);
        }
        return toResponseDTO(pagamento);
    }

    @Override
    public PagamentoResponseDTO buscarPorPedidoId(Long pedidoId) {
        try {
            Pagamento pagamento = em.createQuery(
                "SELECT p FROM Pagamento p WHERE p.pedido.id = :pedidoId", 
                Pagamento.class)
                .setParameter("pedidoId", pedidoId)
                .getSingleResult();
                
            return toResponseDTO(pagamento);
        } catch (NoResultException e) {
            throw new NotFoundException("Nenhum pagamento encontrado para o pedido ID: " + pedidoId);
        }
    }

    @Override
    @Transactional
    public void atualizarStatus(Long id, String novoStatus) {
        Pagamento pagamento = em.find(Pagamento.class,id);
        if (pagamento == null) {
            throw new NotFoundException("Pagamento não encontrado com ID: " + id);
        }
        
        // Validações adicionais (ex: só permite transição "PENDENTE" → "APROVADO")
        if ("APROVADO".equals(novoStatus)) {
            pagamento.setStatus(novoStatus);
            pagamento.getPedido().setStatus("PAGO"); // Atualiza status do pedido também
        } 
        else{
            pagamento.setStatus(novoStatus);
        }
        
    }

    // Método auxiliar para conversão 
    private PagamentoResponseDTO toResponseDTO(Pagamento pagamento) {
        return new PagamentoResponseDTO(
            pagamento.getId(),
            pagamento.getPedido().getId(),
            pagamento.getMetodo(),
            pagamento.getValor(),
            pagamento.getStatus(),
            pagamento.getDataPagamento()
        );
    }

    @Override
    public List<PagamentoResponseDTO> listarTodos(String status) {
    String query = "SELECT p FROM Pagamento p";
    if (status != null && !status.isBlank()) {
        query += " WHERE p.status = :status";
    }

    TypedQuery<Pagamento> typedQuery = em.createQuery(query, Pagamento.class);
    if (status != null && !status.isBlank()) {
        typedQuery.setParameter("status", status);
    }

    return typedQuery.getResultList()
        .stream()
        .map(this::toResponseDTO)
        .toList();
   }

   @Override
   @Transactional
    public void deletarPagamento(Long id) {
    Pagamento pagamento = em.find(Pagamento.class, id);
    if (pagamento == null) {
        throw new NotFoundException("Pagamento não encontrado com ID: " + id);
    }

    // Valida se o pagamento já foi aprovado (não pode deletar)
    if ("APROVADO".equals(pagamento.getStatus())) {
        throw new IllegalStateException("Pagamento aprovado não pode ser removido!");
    }

    // Remove a associação bidirecional com o pedido
    Pedido pedido = pagamento.getPedido();
    if (pedido != null) {
        pedido.setPagamento(null);
    }

    em.remove(pagamento);
    }
}
