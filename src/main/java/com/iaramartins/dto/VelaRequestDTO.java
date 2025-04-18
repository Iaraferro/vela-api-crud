package com.iaramartins.dto;

import com.iaramartins.model.TipoVela;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
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

    String ingrediente,  // Sem validação (opcional)

    String ritualAssociado  // Sem validação (opcional)
) {
} 
