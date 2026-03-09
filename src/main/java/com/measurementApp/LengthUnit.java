package com.measurementApp;

public enum LengthUnit {
	FEET(12.0), INCHES(1.0), YARDS(36.0), CENTIMETERS(0.393701);

	private final double conversionFactor;

	private LengthUnit(double conversionFactor) {
		this.conversionFactor = conversionFactor;
	}

	public double getConversionFactor() {
		return conversionFactor;
	}

	public double convertToBaseUnit(double value) {
		double base = value * this.getConversionFactor();
		return Math.round(base * 100.0) / 100.0;
	}

	public double convertFromBaseUnit(double baseValue) {
		double convertedValue = baseValue / this.getConversionFactor();
		return Math.round(convertedValue * 100.0) / 100.0;
	}
	
	public static void main(String[] args) {
		double yard = 1.0;
		double inches = LengthUnit.YARDS.convertToBaseUnit(yard);
		System.out.println(yard + " yard is equal to " + inches + " inches"); 
		
		double feet = LengthUnit.FEET.convertFromBaseUnit(inches);
		System.out.println(inches + " inches are equal to " + feet + " feet");
	}
}
