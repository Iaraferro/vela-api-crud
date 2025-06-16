package com.iaramartins.repository;

import com.iaramartins.model.EnderecoEntrega;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class EnderecoEntregaRepository implements PanacheRepository<EnderecoEntrega> {
    
}
