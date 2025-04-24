package com.iaramartins.model;

import java.util.ArrayList;
import java.util.List;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity
public class Vela extends DefaultEntity {
    private String nome;

    @ManyToOne
    @JoinColumn(name = "categoria_id")
    private CategoriaVela categoria;

    @Enumerated(EnumType.STRING) //Isso significa que os valores que estão na classe TipoVela serão valores inseridos na coluna TipoVela da tabela Vela como String
    private TipoVela tipo;

    private String aroma;
    private double preco;
    private String ingrediente; // Exemplo: "Cera de soja, ervas de arruda, óleo essencial de alecrim"
    private String ritualAssociado;// Exemplo: "Banimento de energias negativas"
    private boolean disponivel = true;

    @OneToMany(mappedBy = "vela")
    private List<ItemPedido> itensPedidos = new ArrayList<>();

    public String getNome(){
        return nome;
    }
    public void setNome(String nome){
        this.nome = nome;
    }

    public TipoVela getTipo(){
        return tipo;
    }

    public void setTipo(TipoVela tipo){
        this.tipo = tipo;
    }
    public String getAroma() {
        return aroma;
    }

    public void setAroma(String aroma) {
        this.aroma = aroma;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        if (preco <= 0)
        throw new IllegalArgumentException("Preço deve ser positivo");
        this.preco = preco;
    }

    public String getIngrediente() {
        return ingrediente;
    }

    public void setIngrediente(String ingrediente) {
        this.ingrediente = ingrediente;
    }

    public String getRitualAssociado() {
        return ritualAssociado;
    }

    public void setRitualAssociado(String ritualAssociado) {
        this.ritualAssociado = ritualAssociado;
    }

    public boolean getDisponivel() {
        return disponivel;
    }

    public void setDisponivel(boolean disponivel) {
        this.disponivel = disponivel;
    }

    public List<ItemPedido> getItensPedidos() {
        return itensPedidos;
    }

    public void setItensPedidos(List<ItemPedido> itensPedidos) {
        this.itensPedidos = itensPedidos;
    }
}
