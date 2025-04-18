package com.iaramartins.model;

import java.util.List;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;

@Entity
public class CategoriaVela extends PanacheEntity{
    private String nome;
    private String descricao;
    @OneToMany(mappedBy = "categoria")
    private List<Vela> velas;

    public String getNome(){
        return nome;
    }
    public void setNome(String nome){
        this.nome = nome;
    }
    public String getDescricao(){
        return descricao;
    }
    public void setDescricao(String descricao){
         this.descricao= descricao;  
    }
    
}
