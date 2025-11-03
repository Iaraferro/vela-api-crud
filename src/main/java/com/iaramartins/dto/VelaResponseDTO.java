package com.iaramartins.dto;


import com.iaramartins.model.Vela;

public record VelaResponseDTO(
    Long id,
    String nome,
    double preco,
    
    String ritualAssociado,
    Integer estoque
) {
    // Método para converter entidade Vela em DTO
    public static VelaResponseDTO fromEntity(Vela vela) {
        return new VelaResponseDTO(
            vela.getId(),
            vela.getNome(),
            vela.getPreco(),
            vela.getRitualAssociado(),
            vela.getEstoque()
        );
    }    
} 
