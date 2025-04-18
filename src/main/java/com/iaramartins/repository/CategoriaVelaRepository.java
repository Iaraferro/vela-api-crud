package com.iaramartins.repository;

import com.iaramartins.model.CategoriaVela;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class CategoriaVelaRepository implements PanacheRepository<CategoriaVela> {
    
}
