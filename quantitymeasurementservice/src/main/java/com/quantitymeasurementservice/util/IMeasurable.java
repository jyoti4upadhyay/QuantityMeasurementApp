package com.quantitymeasurementservice.util;
public interface IMeasurable {

    double getConversionFactor();

    default double convertToBaseUnit(double value) {
        return value * getConversionFactor();
    }

    default double convertFromBaseUnit(double baseValue) {
        return baseValue / getConversionFactor();
    }
    default boolean supportsArithmetic(){
        return true;
    }
}
