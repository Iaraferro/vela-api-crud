package com.iaramartins.model;

import java.util.ArrayList;
import java.util.List;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity
public class Vela extends DefaultEntity {
    private String nome;

    @ManyToOne
    @JoinColumn(name = "categoria_id")
    private CategoriaVela categoria;

    @Enumerated(EnumType.STRING) //Isso significa que os valores que estão na classe TipoVela serão valores inseridos na coluna TipoVela da tabela Vela como String

    @ManyToOne
    @JoinColumn(name = "fornecedor_id")
    private Fornecedor fornecedor;

    @ManyToMany
    @JoinTable(
        name = "vela_ingrediente",
        joinColumns = @JoinColumn(name = "vela_id"),
        inverseJoinColumns = @JoinColumn(name = "ingrediente_id")
    )
    private List<Ingrediente> ingredientes;

    @ManyToMany
    @JoinTable(
        name = "vela_aroma",
        joinColumns = @JoinColumn(name = "vela_id"),
        inverseJoinColumns = @JoinColumn(name = "aroma_id")
    )
    private List<Aroma> aromas;

    private double preco;
    private String ritualAssociado;// Exemplo: "Banimento de energias negativas"
    private boolean disponivel = true;
    private Integer estoque;

    @OneToMany(mappedBy = "vela")
    private List<ItemPedido> itensPedidos = new ArrayList<>();

    public String getNome(){
        return nome;
    }
    public void setNome(String nome){
        this.nome = nome;
    }


    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        if (preco <= 0)
        throw new IllegalArgumentException("Preço deve ser positivo");
        this.preco = preco;
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

    public Fornecedor getFornecedor(){
        return fornecedor;
    }
    public void setFornecedor(Fornecedor fornecedor){
        this.fornecedor = fornecedor;
    }

    public Integer getEstoque(){
        return estoque;
    }

    public void setEstoque(Integer estoque){
        this.estoque = estoque;
    }
}
