package com.iaramartins.repository;

import java.util.List;

import com.iaramartins.model.CategoriaVela;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class CategoriaVelaRepository implements PanacheRepository<CategoriaVela> {
    public List<CategoriaVela> findByNome(String nome) {
        return find("nome", nome).list();
    }
    
    // Busca categorias que contêm parte do nome
    public List<CategoriaVela> findByNomeContaining(String nome) {
        return find("nome like ?1", "%" + nome + "%").list();
    }
}
