package com.quantitymeasurementservice.service;

import com.quantitymeasurementservice.dto.ResponseDto;
import com.quantitymeasurementservice.entity.QuantityMeasurementEntity;
import com.quantitymeasurementservice.exception.QuantityMeasurementException;
import com.quantitymeasurementservice.repository.QuantityMeasurementRepository;
import com.quantitymeasurementservice.util.IMeasurable;
import com.quantitymeasurementservice.util.Quantity;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;

@Primary
@Service("quantityMeasurementCoreService")
public class QuantityMeasurementServiceImpl implements IQuantityMeasurementService {

    private final QuantityMeasurementRepository repository;

    public QuantityMeasurementServiceImpl(QuantityMeasurementRepository repository) {
        this.repository = repository;
    }

    @Override
    public Quantity<?> add(Quantity<?> q1, Quantity<?> q2, IMeasurable targetUnit) {
        Quantity result = ((Quantity) q1).add((Quantity) q2, targetUnit);
        saveHistory("ADDITION", q1, q2, result);
        return result;
    }

    @Override
    public Quantity<?> subtract(Quantity<?> q1, Quantity<?> q2, IMeasurable targetUnit) {
        Quantity result = ((Quantity) q1).subtract((Quantity) q2, targetUnit);
        saveHistory("SUBTRACTION", q1, q2, result);
        return result;
    }

    @Override
    public Quantity<?> convert(Quantity<?> quantity, IMeasurable targetUnit) {
        Quantity result = ((Quantity) quantity).convertTo(targetUnit);
        saveHistory("CONVERT", quantity, null, result);
        return result;
    }

    @Override
    public boolean compare(Quantity<?> q1, Quantity<?> q2) {
        boolean result = ((Quantity) q1).equals(q2);
        saveHistory("COMPARISON", q1, q2, result ? 1.0 : 0.0, "BOOLEAN");
        return result;
    }

    @Override
    public double divide(Quantity<?> q1, Quantity<?> q2) {
        if (q2.getValue() == 0) {
            throw new QuantityMeasurementException("Division by zero is not allowed");
        }

        double result = ((Quantity) q1).divide((Quantity) q2);
        saveHistory("DIVISION", q1, q2, result, "RATIO");
        return result;
    }

    @Override
    public List<ResponseDto> getHistory() {
        return repository.findAllByOrderByIdDesc()
                .stream()
                .map(this::toResponseDto)
                .toList();
    }

    private void saveHistory(String operation, Quantity<?> q1, Quantity<?> q2, Quantity<?> result) {
        saveHistory(operation, q1, q2, result.getValue(), result.getUnit().toString());
    }

    private void saveHistory(String operation, Quantity<?> q1, Quantity<?> q2, double resultValue, String resultUnit) {
        QuantityMeasurementEntity entity = new QuantityMeasurementEntity();
        entity.setOperation(operation);
        entity.setValue1(q1.getValue());
        entity.setUnit1(q1.getUnit().toString());
        entity.setValue2(q2 != null ? q2.getValue() : null);
        entity.setUnit2(q2 != null ? q2.getUnit().toString() : null);
        entity.setResultValue(resultValue);
        entity.setResultUnit(resultUnit);

        repository.save(entity);
    }

    private ResponseDto toResponseDto(QuantityMeasurementEntity entity) {
        return new ResponseDto(
                entity.getId(),
                entity.getOperation(),
                entity.getValue1(),
                entity.getUnit1(),
                entity.getValue2(),
                entity.getUnit2(),
                entity.getResultValue() == null ? 0.0 : entity.getResultValue(),
                entity.getResultUnit()
        );
    }
}
