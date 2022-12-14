package com.evowork.enumeration.converter;

import com.evowork.enumeration.LevelSchoolType;

import javax.persistence.AttributeConverter;

public class LevelSchoolTypeConverter implements AttributeConverter<LevelSchoolType, Integer> {

    @Override
    public Integer convertToDatabaseColumn(LevelSchoolType itemType) {
        return itemType != null ? itemType.getValue() : null;
    }

    @Override
    public LevelSchoolType convertToEntityAttribute(Integer value) {
        return value == null ? null : LevelSchoolType.parse(value);
    }
}
