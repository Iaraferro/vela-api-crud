package com.iaramartins.dto;

import com.iaramartins.model.EnderecoEntrega;

public record EnderecoEntregaResponseDTO(
    Long id,
    String rua,
    String numero,
    String complemento,
    String bairro,
    String cidade,
    String estado,
    String cep,
    Long pedidoId
) {
     public static EnderecoEntregaResponseDTO valueOf(EnderecoEntrega endereco) {
        if (endereco == null) {
            return null;
        }
        return new EnderecoEntregaResponseDTO(
            endereco.getId(),
            endereco.getRua(),
            endereco.getNumero(),
            endereco.getComplemento(),
            endereco.getBairro(),
            endereco.getCidade(),
            endereco.getEstado(),
            endereco.getCep(),
            endereco.getPedido() != null ? endereco.getPedido().getId() : null
        );
    }
} 
