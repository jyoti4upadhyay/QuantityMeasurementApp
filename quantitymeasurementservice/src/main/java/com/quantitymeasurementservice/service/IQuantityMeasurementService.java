package com.quantitymeasurementservice.service;

import com.quantitymeasurementservice.dto.ResponseDto;
import com.quantitymeasurementservice.util.IMeasurable;
import com.quantitymeasurementservice.util.Quantity;

import java.util.List;

public interface IQuantityMeasurementService {

    Quantity<?> add(
            Quantity<?> q1,
            Quantity<?> q2,
            IMeasurable targetUnit);

    Quantity<?> subtract(
            Quantity<?> q1,
            Quantity<?> q2,
            IMeasurable targetUnit);

    Quantity<?> convert(
            Quantity<?> quantity,
            IMeasurable targetUnit);

    boolean compare(
            Quantity<?> q1,
            Quantity<?> q2);

    double divide(
            Quantity<?> q1,
            Quantity<?> q2);

    List<ResponseDto> getHistory();
}
