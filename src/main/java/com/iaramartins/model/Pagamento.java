package com.iaramartins.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;

@Entity
public class Pagamento extends PanacheEntity {
    @OneToOne
    @JoinColumn(name = "pedido_id", unique = true)
    public Pedido pedido; // Cada pedido tem um pagamento
    public BigDecimal valor;
    public String metodo; //Pix, Cartão, Boleto
    public boolean aprovado;
    public String status;
    public LocalDateTime dataPagamento;
}
