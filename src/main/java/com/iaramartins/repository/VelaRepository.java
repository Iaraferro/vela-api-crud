package com.iaramartins.repository;

import java.util.List;


import com.iaramartins.model.Vela;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class VelaRepository implements PanacheRepository<Vela> {
     public List<Vela> findByNome(String nome) {
        if(nome == null)
        return null;
        return find("UPPER(nome) LIKE ?1", "%" + nome.toUpperCase() + "%").list();
    }

   

}
