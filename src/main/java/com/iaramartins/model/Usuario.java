package com.iaramartins.model;
import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.Entity;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "tipo_usuario")
public abstract class Usuario extends DefaultEntity {
    private String email;
    private String senha;
    private String role;

    public String getEmail(){
        return email;
    }
    public void setEmail( String email){
        this.email = email;
    }
    public String getSenha(){
        return senha;
    }
    public void setSenha(String senha){
        this.senha= senha;
    }
    public String getRole(){
        return role;
    }
    public void setRole(String role){
        this.role = role;
    }
}
