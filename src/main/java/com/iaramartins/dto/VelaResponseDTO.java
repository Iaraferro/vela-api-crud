package com.iaramartins.dto;

import com.iaramartins.model.TipoVela;
import com.iaramartins.model.Vela;

public record VelaResponseDTO(
    Long id,
    String nome,
    double preco,
    TipoVela tipo,
    String aroma,
    String ingredientes,
    String ritualAssociado
) {
    // Método para converter entidade Vela em DTO
    public static VelaResponseDTO fromEntity(Vela vela) {
        return new VelaResponseDTO(
            vela.id,
            vela.getNome(),
            vela.getPreco(),
            vela.getTipo(),
            vela.getAroma(),
            vela.getIngrediente(),
            vela.getRitualAssociado()
        );
    }    
} 
