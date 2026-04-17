package com.quantitymeasurementservice.dto;
import jakarta.validation.constraints.*;

public class QuantityRequest {

    @NotNull
    private Double value1;

    @NotBlank
    private String unit1;

    @NotNull
    private Double value2;

    @NotBlank
    private String unit2;

    public QuantityRequest() {
    }

    public Double getValue1() {
        return value1;
    }

    public void setValue1(Double value1) {
        this.value1 = value1;
    }

    public String getUnit1() {
        return unit1;
    }

    public void setUnit1(String unit1) {
        this.unit1 = unit1;
    }

    public Double getValue2() {
        return value2;
    }

    public void setValue2(Double value2) {
        this.value2 = value2;
    }

    public String getUnit2() {
        return unit2;
    }

    public void setUnit2(String unit2) {
        this.unit2 = unit2;
    }
}
