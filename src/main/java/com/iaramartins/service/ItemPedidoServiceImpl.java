package com.iaramartins.service;

import java.util.List;

import com.iaramartins.dto.ItemPedidoRequestDTO;
import com.iaramartins.dto.ItemPedidoResponseDTO;
import com.iaramartins.model.ItemPedido;
import com.iaramartins.model.Pedido;
import com.iaramartins.model.Vela;
import com.iaramartins.repository.ItemPedidoRepository;
import com.iaramartins.repository.PedidoRepository;
import com.iaramartins.repository.VelaRepository;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;

@ApplicationScoped
public class ItemPedidoServiceImpl implements ItemPedidoService{
    @Inject
    ItemPedidoRepository itemPedidoRepository;
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

        itemPedidoRepository.persist(item);
        pedido.calcularTotal();
        
        return new ItemPedidoResponseDTO(
            item.id,
            vela.getNome(),
            vela.getTipo(),
            item.getQuantidade(),
            item.getPrecoUnitario()
        );
    }

    @Override
    @Transactional
    public void removerItem(Long itemId) {
        ItemPedido item = itemPedidoRepository.findById(itemId);
        if (item == null) return; // Não faz nada se não existir

        Pedido pedido = item.getPedido();
        itemPedidoRepository.delete(item);
        pedido.calcularTotal();
    }

    @Override
    public List<ItemPedidoResponseDTO> listarItensPorPedido(Long pedidoId) {
        return itemPedidoRepository.list("pedido.id", pedidoId)
            .stream()
            .map(item -> new ItemPedidoResponseDTO(
                item.id,
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
        ItemPedido item = itemPedidoRepository.findById(itemId);
        if (item == null) throw new NotFoundException("Item não existe");

        item.setQuantidade(novaQuantidade);
        item.getPedido().calcularTotal();

        return new ItemPedidoResponseDTO(
            item.id,
            item.getVela().getNome(),
            item.getVela().getTipo(),
            item.getQuantidade(),
            item.getPrecoUnitario()
        );
    }
}
