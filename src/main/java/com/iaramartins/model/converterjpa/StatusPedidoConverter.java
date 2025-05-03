package com.iaramartins.model.converterjpa;

import java.util.Arrays;
import java.util.List;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class StatusPedidoConverter implements AttributeConverter<String, String> {
    private static final List<String> STATUS_VALIDOS = 
        Arrays.asList("PENDENTE", "PAGO", "CANCELADO", "ENVIADO", "ENTREGUE");

    @Override
    public String convertToDatabaseColumn(String status) {
        if (status == null) {
            return "PENDENTE"; // Valor padrão
        }
        if (!STATUS_VALIDOS.contains(status)) {
            throw new IllegalArgumentException("Status de pedido inválido: " + status);
        }
        return status;
    }

    @Override
    public String convertToEntityAttribute(String dbData) {
        return dbData; // Já validado no banco
    }
}
