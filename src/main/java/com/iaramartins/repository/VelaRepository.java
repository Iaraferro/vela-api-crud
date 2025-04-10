package com.iaramartins.repository;

import java.util.List;

import com.iaramartins.model.TipoVela;
import com.iaramartins.model.Vela;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class VelaRepository implements PanacheRepository<Vela> {
     public List<Vela> findByNome(String nome) {
        return find("nome LIKE ?1", "%" + nome + "%").list();
    }

    public List<Vela> findByTipo(TipoVela tipo) {
        return find("tipo", tipo).list();
    }

}
