package com.iaramartins.model;



import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class ItemPedido extends PanacheEntity{
    
   @ManyToOne
   @JoinColumn(name = "pedido_Id")
   public Pedido pedido;

   @ManyToOne
   @JoinColumn(name = "vela_id")
   public Vela vela;

   public int quantidade;
   public Double precoUnitario;



}
