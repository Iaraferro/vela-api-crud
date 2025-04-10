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
            vela.nome,
            vela.preco,
            vela.tipo,
            vela.aroma,
            vela.ingrediente,
            vela.ritualAssociado
        );
    }    
} 
