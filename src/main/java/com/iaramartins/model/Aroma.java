package com.iaramartins.model;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;

import jakarta.persistence.ManyToMany;

@Entity
public class Aroma extends DefaultEntity {

    
    
    @Column(name = "essenciaAromatica")
    private String essenciaAromatica;

     @ManyToMany(mappedBy = "aromas")
    private List<Vela> velas;

    public String getEssenciaAromatica(){
        return essenciaAromatica;
    }
    public void setEssenciaAromatica(String essenciaAromatica){
        this.essenciaAromatica = essenciaAromatica;
    }

    
}
