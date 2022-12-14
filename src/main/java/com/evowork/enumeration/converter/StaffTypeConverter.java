package com.evowork.enumeration.converter;

import com.evowork.enumeration.StaffType;

import javax.persistence.AttributeConverter;

public class StaffTypeConverter implements AttributeConverter<StaffType, Integer> {

    @Override
    public Integer convertToDatabaseColumn(StaffType itemType) {
        return itemType != null ? itemType.getValue() : null;
    }

    @Override
    public StaffType convertToEntityAttribute(Integer value) {
        return value == null ? null : StaffType.parse(value);
    }
}
