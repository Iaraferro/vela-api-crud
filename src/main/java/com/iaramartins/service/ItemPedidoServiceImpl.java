package com.iaramartins.service;

import java.util.List;

import com.iaramartins.dto.ItemPedidoRequestDTO;
import com.iaramartins.dto.ItemPedidoResponseDTO;
import com.iaramartins.model.ItemPedido;
import com.iaramartins.model.Pedido;
import com.iaramartins.model.TipoVela;
import com.iaramartins.model.Vela;
import com.iaramartins.repository.PedidoRepository;
import com.iaramartins.repository.VelaRepository;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
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

        if (dto.quantidade() <= 0) {
            throw new IllegalArgumentException("Quantidade deve ser positiva");
        }
        
        Pedido pedido = pedidoRepository.findById(pedidoId);
        if (pedido == null) {
            throw new NotFoundException("Pedido não encontrado");
        }

        Vela vela = velaRepository.findById(dto.velaId());
        if (vela == null) {
            throw new NotFoundException("Vela não encontrada");
        }
        if (vela.getEstoque() == null) {
            throw new IllegalArgumentException("Estoque da vela não pode ser nulo");
        }
        if (dto.quantidade() > vela.getEstoque()) {
        throw new IllegalArgumentException("Quantidade solicitada excede o estoque disponível");
        }
        vela.setEstoque(vela.getEstoque() - dto.quantidade());

        

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
    public boolean verificarDisponibilidadeEstoque(Long velaId, int quantidade) {
        Vela vela = velaRepository.findById(velaId);
        if (vela == null) return false;
        return vela.getEstoque() != null && vela.getEstoque() >= quantidade;
    }

    @Override
    @Transactional
    public void removerItem(Long itemId) {
    ItemPedido item = em.find(ItemPedido.class, itemId);
    if (item == null) {
        throw new NotFoundException("Item não encontrado");
    }
    Pedido pedido = item.getPedido();
    em.remove(item);
    em.flush(); // Ensure the deletion is persisted
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

    @Override
    public ItemPedidoResponseDTO buscarItemPorId(Long itemId) {
    ItemPedido item = em.find(ItemPedido.class, itemId);
    if (item == null) {
        throw new NotFoundException("Item não encontrado");
    }
    return new ItemPedidoResponseDTO(
        item.getId(),
        item.getVela().getNome(),
        item.getVela().getTipo(),
        item.getQuantidade(),
        item.getPrecoUnitario()
    );
}

@Override
public List<ItemPedidoResponseDTO> listarItensPorPedidoComFiltros(
    Long pedidoId, 
    TipoVela tipoVela, 
    Integer quantidadeMinima
) {
    String query = "SELECT i FROM ItemPedido i WHERE i.pedido.id = :pedidoId";
    
    // Filtros dinâmicos
    if (tipoVela != null) {
        query += " AND i.vela.tipo = :tipoVela";
    }
    if (quantidadeMinima != null) {
        query += " AND i.quantidade >= :quantidadeMinima";
    }

    TypedQuery<ItemPedido> typedQuery = em.createQuery(query, ItemPedido.class)
        .setParameter("pedidoId", pedidoId);
    
    if (tipoVela != null) {
        typedQuery.setParameter("tipoVela", tipoVela);
    }
    if (quantidadeMinima != null) {
        typedQuery.setParameter("quantidadeMinima", quantidadeMinima);
    }

    return typedQuery.getResultList()
        .stream()
        .map(item -> new ItemPedidoResponseDTO(
            item.getId(),
            item.getVela().getNome(),
            item.getVela().getTipo(),
            item.getQuantidade(),
            item.getPrecoUnitario()
        ))
        .toList();
    }
}
