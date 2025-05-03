package com.iaramartins.model.converterjpa;

import com.iaramartins.model.TipoVela;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class TipoVelaConverter implements AttributeConverter<TipoVela, String> {
    @Override
    public String convertToDatabaseColumn(TipoVela tipo) {
        return (tipo == null) ? null : tipo.name();
    }

    @Override
    public TipoVela convertToEntityAttribute(String dbData) {
        return (dbData == null || dbData.isEmpty()) ? null : TipoVela.valueOf(dbData);
    }
}
