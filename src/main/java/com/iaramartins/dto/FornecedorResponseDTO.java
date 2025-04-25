package com.iaramartins.dto;

import java.time.LocalDateTime;

import com.iaramartins.model.Fornecedor;

public record FornecedorResponseDTO(
    Long id,
    String nome,
    String cnpj,
    String telefone,
    LocalDateTime createdAt
) {
     public static FornecedorResponseDTO fromEntity(Fornecedor fornecedor) {
        return new FornecedorResponseDTO(
            fornecedor.getId(),
            fornecedor.getNome(),
            fornecedor.getCnpj(),
            fornecedor.getTelefone(),
            fornecedor.getCreatedAt()
        );

     }    
}
