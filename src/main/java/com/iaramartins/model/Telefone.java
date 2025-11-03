package com.iaramartins.model;

import jakarta.persistence.Entity;

@Entity
public class Telefone extends Pessoa {
    private String numero;
    private String codigoArea;

    public String getNumero(){
        return numero;
    }
    public void setNumero(String numero){
        this.numero = numero;
    }
    public String getCodigoArea(){
        return codigoArea;
    }
    public void setCodigoArea( String codigoArea){
        this.codigoArea = codigoArea;
    }
}

