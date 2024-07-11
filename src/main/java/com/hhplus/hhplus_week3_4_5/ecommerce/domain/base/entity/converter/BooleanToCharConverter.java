package com.hhplus.hhplus_week3_4_5.ecommerce.domain.base.entity.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class BooleanToCharConverter implements AttributeConverter<Boolean, Character> {

    @Override
    public Character convertToDatabaseColumn(Boolean attribute) {
        return (attribute != null && attribute) ? 'Y' : 'N';
    }

    @Override
    public Boolean convertToEntityAttribute(Character dbData) {
        return dbData != null && dbData.equals('Y');
    }
}
