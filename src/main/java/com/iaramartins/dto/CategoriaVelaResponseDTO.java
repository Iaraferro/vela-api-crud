package com.iaramartins.dto;

import com.iaramartins.model.CategoriaVela;

public record CategoriaVelaResponseDTO(
    Long id,
    String nome,
    String descricao
) {
    public static CategoriaVelaResponseDTO fromEntity(CategoriaVela categoria){
        return new CategoriaVelaResponseDTO(
            categoria.getId(),
            categoria.getNome(),
            categoria.getDescricao()
        );
    }
}