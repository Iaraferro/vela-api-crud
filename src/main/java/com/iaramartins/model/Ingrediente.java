package com.iaramartins.model;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;

@Entity
public class Ingrediente extends DefaultEntity {
    private String recipiente;
    private String pavio;
    private String tipoCera;

    @ManyToMany(mappedBy = "ingredientes")
    private List<Vela> velas;

    public String getRecipiente(){
        return recipiente;
    }
    public void setRecipiente(String recipiente){
        this.recipiente =recipiente;
    }
     public String getPavio(){
        return pavio;
    }
    public void setPavio(String pavio){
        this.pavio = pavio;
    }
     public String getTipoCera(){
        return tipoCera;
    }
    public void setTipoCera(String tipoCera){
        this.tipoCera = tipoCera;
    }
}
