package com.iaramartins.repository;

import java.util.List;

import com.iaramartins.model.ItemPedido;
import com.iaramartins.model.Pedido;
import com.iaramartins.model.Vela;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ItemPedidoRepository implements PanacheRepository<ItemPedido> {
     
    public List<ItemPedido> findByPedido(Pedido pedido) {
        return find("pedido", pedido).list();
    }
    
    public List<ItemPedido> findByVela(Vela vela) {
        return find("vela", vela).list();
    }
    
    public List<ItemPedido> findByQuantidadeMaiorQue(int quantidade) {
        return find("quantidade > ?1", quantidade).list();
    }
}
