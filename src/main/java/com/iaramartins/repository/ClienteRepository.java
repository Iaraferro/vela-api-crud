package com.iaramartins.repository;

import java.util.List;

import com.iaramartins.model.Cliente;

import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;


@ApplicationScoped
public class ClienteRepository implements PanacheRepository<Cliente> {
    //public List<Cliente> findByNome(String nome) {
      //  return find("nome like ?1", "%" + nome + "%").list();
   // }
    public PanacheQuery<Cliente> findByNome(String nome){
        if(nome == null)
        return null;
        return find("UPPER(nome) LIKE ?1 ", "%" + nome.toUpperCase()+ "%");
    }
    
    // Busca clientes por telefone
    public List<Cliente> findByTelefone(String telefone) {
        return find("telefone", telefone).list();
    }
}
