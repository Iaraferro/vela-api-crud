package com.iaramartins.model;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;

@Entity
public class Fornecedor extends Pessoa {
    private String cnpj;

    @OneToMany(mappedBy = "fornecedor", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Vela> velas;

   
    public String getCnpj() {
        return cnpj;
    }
    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }
  
    public List<Vela> getVelas() {
        return velas;
    }
    public void setVelas(List<Vela> velas) {
        this.velas = velas;
    }
}
