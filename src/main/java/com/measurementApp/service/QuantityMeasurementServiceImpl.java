package com.measurementApp.service;

import com.measurementApp.dto.QuantityDTO;
import com.measurementApp.entity.QuantityMeasurementEntity;
import com.measurementApp.repository.IQuantityMeasurementRepository;

public class QuantityMeasurementServiceImpl implements IQuantityMeasurementService {

    private IQuantityMeasurementRepository repo;

    public QuantityMeasurementServiceImpl(IQuantityMeasurementRepository repo) {
        this.repo = repo;
    }

    private double toInch(QuantityDTO q) {

        if(q.getUnit().equalsIgnoreCase("FEET")) {
            return q.getValue()*12;
        }

        return q.getValue();
    }

    @Override
    public boolean compare(QuantityDTO q1, QuantityDTO q2) {

        double v1 = toInch(q1);
        double v2 = toInch(q2);

        boolean result = v1 == v2;

        repo.save(new QuantityMeasurementEntity(
                "COMPARE",
                q1.getValue()+" "+q1.getUnit(),
                q2.getValue()+" "+q2.getUnit(),
                String.valueOf(result)));

        return result;
    }

    @Override
    public QuantityDTO add(QuantityDTO q1, QuantityDTO q2) {

        double result = toInch(q1) + toInch(q2);

        repo.save(new QuantityMeasurementEntity(
                "ADD",
                q1.getValue()+" "+q1.getUnit(),
                q2.getValue()+" "+q2.getUnit(),
                result+" INCH"));

        return new QuantityDTO(result,"INCH");
    }

    @Override
    public QuantityDTO subtract(QuantityDTO q1, QuantityDTO q2) {

        double result = toInch(q1) - toInch(q2);

        repo.save(new QuantityMeasurementEntity(
                "SUBTRACT",
                q1.getValue()+" "+q1.getUnit(),
                q2.getValue()+" "+q2.getUnit(),
                result+" INCH"));

        return new QuantityDTO(result,"INCH");
    }

    @Override
    public double divide(QuantityDTO q1, QuantityDTO q2) {

        double result = toInch(q1) / toInch(q2);

        repo.save(new QuantityMeasurementEntity(
                "DIVIDE",
                q1.getValue()+" "+q1.getUnit(),
                q2.getValue()+" "+q2.getUnit(),
                String.valueOf(result)));

        return result;
    }

    @Override
    public QuantityDTO convert(QuantityDTO q, String target) {

        double inch = toInch(q);

        double result = inch;

        if(target.equalsIgnoreCase("FEET")) {
            result = inch/12;
        }

        repo.save(new QuantityMeasurementEntity(
                "CONVERT",
                q.getValue()+" "+q.getUnit(),
                target,
                result+" "+target));

        return new QuantityDTO(result,target);
    }
}