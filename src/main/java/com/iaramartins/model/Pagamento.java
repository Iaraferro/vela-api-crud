package com.iaramartins.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import com.iaramartins.model.converterjpa.StatusPagamentoConverter;

import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;

@Entity
public class Pagamento extends DefaultEntity {
    @OneToOne
    @JoinColumn(name = "pedido_id", unique = true)
    private Pedido pedido; // Cada pedido tem um pagamento

    private BigDecimal valor;
    private String metodo; //Pix, Cartão, Boleto
    @Convert(converter = StatusPagamentoConverter.class)
    private String status;
    private LocalDateTime dataPagamento;

    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        // Mantém a consistência bidirecional
        if (this.pedido != null && !this.pedido.equals(pedido)) {
            this.pedido.setPagamento(null);
        }
        this.pedido = pedido;
        if (pedido != null && pedido.getPagamento() != this) {
            pedido.setPagamento(this);
        }
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        if (valor == null || valor.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Valor deve ser positivo");
        }
        this.valor = valor;
    }

    public String getMetodo() {
        return metodo;
    }

    public void setMetodo(String metodo) {
        if (metodo == null || metodo.isBlank()) {
            throw new IllegalArgumentException("Método de pagamento não pode ser vazio");
        }
        this.metodo = metodo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        if (status == null || !List.of("PENDENTE", "APROVADO", "RECUSADO").contains(status)) {
            throw new IllegalArgumentException("Status inválido");
        }
        this.status = status;
    }

    public LocalDateTime getDataPagamento() {
        return dataPagamento;
    }

    public void setDataPagamento(LocalDateTime dataPagamento) {
        if (dataPagamento == null || dataPagamento.isAfter(LocalDateTime.now())) {
            throw new IllegalArgumentException("Data de pagamento inválida");
        }
        this.dataPagamento = dataPagamento;
    }

    // Business methods
    public void aprovar() {
        this.setStatus("APROVADO");
        if (pedido != null) {
            pedido.setStatus("PAGO");
        }
    }

    public void recusar() {
        this.setStatus("RECUSADO");
        if (pedido != null) {
            pedido.setStatus("CANCELADO");
        }
    }
}
