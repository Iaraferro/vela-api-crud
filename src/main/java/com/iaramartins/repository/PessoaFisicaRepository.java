package com.iaramartins.repository;

import com.iaramartins.model.PessoaFisica;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class PessoaFisicaRepository implements PanacheRepository<PessoaFisica>{
    
}
