package com.iaramartins.repository;

import java.time.LocalDateTime;
import java.util.List;

import com.iaramartins.model.Cliente;
import com.iaramartins.model.Pedido;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;


@ApplicationScoped
public class PedidoRepository implements PanacheRepository<Pedido>{
    // Busca pedidos por cliente
    public List<Pedido> findByCliente(Cliente cliente) {
        return find("cliente", cliente).list();
    }
    
    // Busca pedidos por status
    public List<Pedido> findByStatus(String status) {
        return find("status", status).list();
    }
    
    public List<Pedido> findByPeriodo(LocalDateTime inicio, LocalDateTime fim) {
        return find("data between ?1 and ?2", inicio, fim).list();
    }
    
    
}
