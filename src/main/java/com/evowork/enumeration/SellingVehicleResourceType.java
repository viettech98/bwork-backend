package com.evowork.enumeration;

import com.fasterxml.jackson.annotation.JsonValue;

import java.util.HashMap;
import java.util.Map;

/**
 * Status type
 * @author tuanlt
 * @since 1.0
 */
public enum SellingVehicleResourceType {

    IMAGE(1),
    VIDEO(2);

    private int value;
    private static Map<Integer, SellingVehicleResourceType> valueMapping = new HashMap<>();

    static {
        for (SellingVehicleResourceType itemType : SellingVehicleResourceType.values()) {
            valueMapping.put(itemType.getValue(), itemType);
        }
    }

    SellingVehicleResourceType(int value) {
        this.value = value;
    }

    @JsonValue
    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public static SellingVehicleResourceType parse(int value) {
        return valueMapping.get(value);
    }
}
