package com.hhplus.ecommerce.domain.base.entity.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Slf4j
@Converter
public class StringListConverter implements AttributeConverter<List<String>, String> {
    private static final String SPLIT_CHAR = ";";

    @Override
    public String convertToDatabaseColumn(List<String> stringList) {
        return stringList != null ? String.join(SPLIT_CHAR, stringList) : "";
    }

    @Override
    public List<String> convertToEntityAttribute(String string) {
        return string != null ? Arrays.asList(string.split(SPLIT_CHAR)) : new ArrayList<>();
    }
}