package com.iaramartins.repository;

import com.iaramartins.model.Telefone;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class TelefoneRespository implements PanacheRepository<Telefone> {
    
}
