package com.iaramartins.service;

import java.util.List;

import com.iaramartins.dto.ItemPedidoRequestDTO;
import com.iaramartins.dto.ItemPedidoResponseDTO;
import com.iaramartins.model.ItemPedido;
import com.iaramartins.model.Pedido;
import com.iaramartins.model.Vela;
import com.iaramartins.repository.PedidoRepository;
import com.iaramartins.repository.VelaRepository;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;

@ApplicationScoped
public class ItemPedidoServiceImpl implements ItemPedidoService{
    @Inject
   EntityManager em;
    @Inject
    PedidoRepository pedidoRepository;
    @Inject
    VelaRepository velaRepository;

    @Override
    @Transactional
    public ItemPedidoResponseDTO adicionarItem(Long pedidoId, ItemPedidoRequestDTO dto) {
        Pedido pedido = pedidoRepository.findById(pedidoId);
        if (pedido == null) {
            throw new NotFoundException("Pedido não encontrado");
        }

        Vela vela = velaRepository.findById(dto.velaId());
        if (vela == null) {
            throw new NotFoundException("Vela não encontrada");
        }

        ItemPedido item = new ItemPedido();
        item.setPedido(pedido);
        item.setVela(vela);
        item.setQuantidade(dto.quantidade());
        item.setPrecoUnitario(vela.getPreco()); // Pega o preço atual da vela

        em.persist(item);
        pedido.calcularTotal();
        
        return new ItemPedidoResponseDTO(
            item.getId(),
            vela.getNome(),
            vela.getTipo(),
            item.getQuantidade(),
            item.getPrecoUnitario()
        );
    }

    @Override
    @Transactional
    public void removerItem(Long itemId) {
        ItemPedido item = em.find(ItemPedido.class, itemId);
        if (item == null) return; // Não faz nada se não existir
        Pedido pedido = item.getPedido();
        em.remove(item);
        pedido.calcularTotal();
    }

    @Override
    public List<ItemPedidoResponseDTO> listarItensPorPedido(Long pedidoId) {
        List<ItemPedido> itens = em.createQuery(
            "SELECT i FROM ItemPedido i WHERE i.pedido.id = :pedidoId", 
            ItemPedido.class)
            .setParameter("pedidoId", pedidoId)
            .getResultList();

            return itens.stream()
            .map(item -> new ItemPedidoResponseDTO(
                item.getId(),
                item.getVela().getNome(),
                item.getVela().getTipo(),
                item.getQuantidade(),
                item.getPrecoUnitario()
            ))
            .toList();
    }

    @Override
    @Transactional
    public ItemPedidoResponseDTO atualizarQuantidade(Long itemId, int novaQuantidade) {
        ItemPedido item = em.find(ItemPedido.class, itemId);
        if (item == null) throw new NotFoundException("Item não existe");

        item.setQuantidade(novaQuantidade);
        item.getPedido().calcularTotal();

        return new ItemPedidoResponseDTO(
            item.getId(),
            item.getVela().getNome(),
            item.getVela().getTipo(),
            item.getQuantidade(),
            item.getPrecoUnitario()
        );
    }
}
