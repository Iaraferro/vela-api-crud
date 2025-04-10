package com.iaramartins.model;

import java.util.ArrayList;
import java.util.List;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.OneToMany;

@Entity
public class Vela extends PanacheEntity {
    public String nome;

    @Enumerated(EnumType.STRING) //Isso significa que os valores que estão na classe TipoVela serão valores inseridos na coluna TipoVela da tabela Vela como String
    public TipoVela tipo;

    public String aroma;
    public double preco;
    public String ingrediente; // Exemplo: "Cera de soja, ervas de arruda, óleo essencial de alecrim"
    public String ritualAssociado;// Exemplo: "Banimento de energias negativas"
    public boolean disponivel = true;
    @OneToMany(mappedBy = "vela")
public List<ItemPedido> itensPedidos = new ArrayList<>();
}
