package com.measurementApp.app;

import com.measurementApp.controller.QuantityMeasurementController;
import com.measurementApp.dto.QuantityDTO;
import com.measurementApp.repository.QuantityMeasurementDBRepository;
import com.measurementApp.service.QuantityMeasurementServiceImpl;

public class QuantityMeasurementApp {

    public static void main(String[] args) {

        QuantityMeasurementDBRepository repo=new QuantityMeasurementDBRepository();

        QuantityMeasurementServiceImpl service=new QuantityMeasurementServiceImpl(repo);

        QuantityMeasurementController controller=new QuantityMeasurementController(service);

        QuantityDTO q1=new QuantityDTO(1,"FEET");
        QuantityDTO q2=new QuantityDTO(12,"INCH");

        controller.performComparison(q1,q2);
        controller.performAddition(q1,q2);
        controller.performConversion(q1,"INCH");
        controller.performSubtraction(q1,q2);
        controller.performDivision(q1,q2);

    }
}