package com.iaramartins.repository;

import com.iaramartins.model.Ingrediente;


import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class IngredienteRepository implements PanacheRepository<Ingrediente> {
    
}
