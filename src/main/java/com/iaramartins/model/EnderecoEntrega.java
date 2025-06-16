package com.iaramartins.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
public class EnderecoEntrega extends DefaultEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String rua;

    @NotBlank
    private String numero;

    private String complemento;

    @NotBlank
    private String bairro;

    @NotBlank
    private String cidade;

    @NotBlank
    private String estado;

    @NotBlank
    @Size(min = 8, max = 9)
    private String cep;

    @OneToOne
    @JoinColumn(name = "pedido_id", nullable = false, unique = true)
    private Pedido pedido;

    public Long getId() {
    return id;
}

public void setId(Long id) {
    this.id = id;
}

public String getRua() {
    return rua;
}

public void setRua(String rua) {
    this.rua = rua;
}

public String getNumero() {
    return numero;
}

public void setNumero(String numero) {
    this.numero = numero;
}

public String getComplemento() {
    return complemento;
}

public void setComplemento(String complemento) {
    this.complemento = complemento;
}

public String getBairro() {
    return bairro;
}

public void setBairro(String bairro) {
    this.bairro = bairro;
}

public String getCidade() {
    return cidade;
}

public void setCidade(String cidade) {
    this.cidade = cidade;
}

public String getEstado() {
    return estado;
}

public void setEstado(String estado) {
    this.estado = estado;
}

public String getCep() {
    return cep;
}

public void setCep(String cep) {
    this.cep = cep;
}

public Pedido getPedido() {
    return pedido;
}

public void setPedido(Pedido pedido) {
    this.pedido = pedido;
}

}
