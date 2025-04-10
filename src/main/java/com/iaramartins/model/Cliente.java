package com.iaramartins.model;

import java.util.ArrayList;
import java.util.List;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;

@Entity
public class Cliente extends PanacheEntity{
    public String nome;
    public String email;
    public String telefone;

    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL, orphanRemoval = true)
    public List<Pedido> pedidos = new ArrayList<>(); // ← Adicione esta linha

    // método para sincronizar o relacionamento
    public void addPedido(Pedido pedido) {
        pedidos.add(pedido);
        pedido.cliente = this;
    }

}
