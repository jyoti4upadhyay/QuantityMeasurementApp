package com.measurementApp.controller;

import com.measurementApp.dto.QuantityDTO;
import com.measurementApp.service.IQuantityMeasurementService;

public class QuantityMeasurementController {
    private IQuantityMeasurementService service;
    public QuantityMeasurementController(IQuantityMeasurementService service) {
        this.service = service;
    }

    public void performComparison(QuantityDTO q1, QuantityDTO q2) {
        boolean result=service.compare(q1,q2);
        System.out.println("Comparison result = "+result);
    }

    public void performAddition(QuantityDTO q1, QuantityDTO q2) {
        QuantityDTO result=service.add(q1,q2);
        System.out.println("Addition result = "+result.getValue()+" "+result.getUnit());
    }

    public void performSubtraction(QuantityDTO q1, QuantityDTO q2) {
        QuantityDTO result=service.subtract(q1,q2);
        System.out.println("Subtraction result = "+result.getValue()+" "+result.getUnit());
    }

    public void performDivision(QuantityDTO q1, QuantityDTO q2) {
        double result=service.divide(q1,q2);
        System.out.println("Division result = "+result);
    }

    public void performConversion(QuantityDTO q,String target) {
        QuantityDTO result=service.convert(q,target);
        System.out.println("Converted value = "+result.getValue()+" "+result.getUnit());
    }
}