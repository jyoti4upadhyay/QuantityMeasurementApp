package com.quantitymeasurementservice.enums;

import com.quantitymeasurementservice.util.IMeasurable;
public enum VolumeUnit implements IMeasurable{
	LITRE(1.0),
	MILLILITRE(0.001),
	GALLON(3.78541);
	
	 private final double conversionFactor;
	 private VolumeUnit(double conversionFactor) {
	        this.conversionFactor = conversionFactor;
	 }
	 
	  public double getConversionFactor() {
	        return conversionFactor;
	    }
	

	   

	  
	
	

}
