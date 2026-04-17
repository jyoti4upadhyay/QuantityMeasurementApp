package com.quantitymeasurementservice.util;

import com.quantitymeasurementservice.enums.LengthUnit;
import com.quantitymeasurementservice.enums.TemperatureUnit;
import com.quantitymeasurementservice.enums.VolumeUnit;
import com.quantitymeasurementservice.enums.WeightUnit;

public class UnitConverter {

    public static IMeasurable fromString(String unit) {

        try {
            return LengthUnit.valueOf(unit.toUpperCase());
        } catch (Exception ignored) {}

        try {
            return WeightUnit.valueOf(unit.toUpperCase());
        } catch (Exception ignored) {}

        try {
            return TemperatureUnit.valueOf(unit.toUpperCase());
        } catch (Exception ignored) {}

        try {
            return VolumeUnit.valueOf(unit.toUpperCase());
        } catch (Exception ignored) {}

        throw new IllegalArgumentException("Invalid unit: " + unit);
    }
}
