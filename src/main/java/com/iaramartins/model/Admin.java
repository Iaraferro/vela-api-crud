package com.iaramartins.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("ADMIN")
public class Admin extends Usuario{
    private String departamento;

    public String getDepartamento(){
        return departamento;
    }
    public void setDepartamento( String departamento){
          this.departamento = departamento;
    }
}
