package com.iaramartins.model;

import jakarta.persistence.Entity;



@Entity
public  class Pessoa extends DefaultEntity {
    
    private String nome;

    public String getNome(){
        return nome;
    }
    public void setNome( String nome){
         this.nome = nome;
    }
}
