package com.iaramartins.service;


import java.time.LocalDateTime;

import com.iaramartins.dto.PagamentoRequestDTO;
import com.iaramartins.dto.PagamentoResponseDTO;
import com.iaramartins.model.Pagamento;
import com.iaramartins.model.Pedido;
import com.iaramartins.repository.PagamentoRepository;
import com.iaramartins.repository.PedidoRepository;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;

@ApplicationScoped
public class PagamentoServiceImpl implements PagamentoService{
     @Inject
    PagamentoRepository pagamentoRepository;

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
        
        pagamentoRepository.persist(pagamento);
        pedido.setPagamento(pagamento);
        // Retorna o DTO de resposta
        return toResponseDTO(pagamento);
    }

    @Override
    public PagamentoResponseDTO buscarPorId(Long id) {
        Pagamento pagamento = pagamentoRepository.findById(id);
        if (pagamento == null) {
            throw new NotFoundException("Pagamento não encontrado com ID: " + id);
        }
        return toResponseDTO(pagamento);
    }

    @Override
    public PagamentoResponseDTO buscarPorPedidoId(Long pedidoId) {
        Pagamento pagamento = pagamentoRepository.find("pedido.id", pedidoId).firstResult();
        if (pagamento == null) {
            throw new NotFoundException("Nenhum pagamento encontrado para o pedido ID: " + pedidoId);
        }
        return toResponseDTO(pagamento);
    }

    @Override
    @Transactional
    public void atualizarStatus(Long id, String novoStatus) {
        Pagamento pagamento = pagamentoRepository.findById(id);
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
            pagamento.id,
            pagamento.getPedido().id,
            pagamento.getMetodo(),
            pagamento.getValor(),
            pagamento.getStatus(),
            pagamento.getDataPagamento()
        );
    }
}
