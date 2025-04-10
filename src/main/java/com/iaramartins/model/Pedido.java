package com.iaramartins.model;

import java.time.LocalDateTime;
import java.util.List;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;

@Entity
public class Pedido extends PanacheEntity{
    @ManyToOne
    @JoinColumn(name = "cliente_id")
    public Cliente cliente;

    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL)
    public List<ItemPedido> itens;

    @OneToOne(mappedBy = "pedido", cascade = CascadeType.ALL, orphanRemoval = true)
    public Pagamento pagamento;

    public LocalDateTime data = LocalDateTime.now();
    public Double total;

    public String status;

    // Método para calcular o total (opcional)
    public void calcularTotal() {
        this.total = itens.stream()
            .mapToDouble(item -> item.precoUnitario * item.quantidade)
            .sum();
    }


}
