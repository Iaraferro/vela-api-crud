package com.iaramartins.repository;

import java.util.List;

import com.iaramartins.model.Fornecedor;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class FornecedorRepository implements PanacheRepository<Fornecedor> {
    @Inject
    EntityManager em;

    public void persist(Fornecedor fornecedor){
        em.persist(fornecedor);
    }
    public List<Fornecedor> listAll(){
        return em.createQuery("FROM Fornecedor", Fornecedor.class).getResultList();
    }
    public Fornecedor findById(Long id){
        return em.find(Fornecedor.class, id);
    }

    @Transactional
    public void delete(Long id){
       Fornecedor fornecedor = findById(id);
       if(fornecedor != null){
        em.remove(fornecedor);
       }
    }
}
