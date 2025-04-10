package com.iaramartins.repository;

import com.iaramartins.model.Pedido;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;


@ApplicationScoped
public class PedidoRepository implements PanacheRepository<Pedido>{
    
}
