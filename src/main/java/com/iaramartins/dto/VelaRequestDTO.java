package com.iaramartins.dto;

import com.iaramartins.model.TipoVela;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

public record VelaRequestDTO(
    @NotBlank(message = "O nome não pode ser vazio!")
    @Size(min=3, max = 100, message = "Nome deve ter 3-100 caracteres")
     String nome,

    @Positive(message = "O preço deve ser positivo")
    double preco,

    TipoVela tipo,  // Campo opcional (se você quiser manter)

    @NotBlank(message = "O aroma não pode ser vazio!")
    String aroma,

    String ingrediente,  

    String ritualAssociado,
    @PositiveOrZero(message = "Estoque não pode ser negativo")
    Integer estoque
  
) {
     public VelaRequestDTO {
        estoque = estoque != null ? estoque : 0; // Valor padrão
    }
    public static VelaRequestDTO createWithoutStock(String nome, double preco, TipoVela tipo,String aroma, String ingrediente, String ritualAssociado) {
        return new VelaRequestDTO(nome, preco, tipo, aroma, ingrediente, ritualAssociado, 0);
    }
} 
