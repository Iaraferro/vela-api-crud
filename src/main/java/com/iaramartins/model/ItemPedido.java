package com.iaramartins.model;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class ItemPedido extends DefaultEntity{
    
   @ManyToOne
   @JoinColumn(name = "pedido_Id")
   private Pedido pedido;

   @ManyToOne
   @JoinColumn(name = "vela_id")
   private Vela vela;

   private int quantidade;
   private Double precoUnitario;


   public Pedido getPedido() {
      return pedido;
  }

  public void setPedido(Pedido pedido) {
      this.pedido = pedido;
  }

  public Vela getVela() {
      return vela;
  }

  public void setVela(Vela vela) {
      this.vela = vela;
  }

  public int getQuantidade() {
      return quantidade;
  }

  public void setQuantidade(int quantidade) {
      if (quantidade <= 0) {
          throw new IllegalArgumentException("Quantidade deve ser positiva");
      }
      this.quantidade = quantidade;
  }

  public Double getPrecoUnitario() {
      return precoUnitario;
  }

  public void setPrecoUnitario(Double precoUnitario) {
      if (precoUnitario <= 0) {
          throw new IllegalArgumentException("Preço unitário deve ser positivo");
      }
      this.precoUnitario = precoUnitario;
  }

  // --- Métodos auxiliares ---
  public Double calcularSubtotal() {
      return precoUnitario * quantidade;
  }


}
