package com.iaramartins.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record ItemPedidoRequestDTO( 
    @NotNull(message = "ID da vela é obrigatório")
    Long velaId,
    @Positive(message = "Quantiade deve ser maior que zero")
    int quantidade,
    @Positive(message = "Preço unitário deve ser positivo")
    Double precoUnitario
 ) {

}
