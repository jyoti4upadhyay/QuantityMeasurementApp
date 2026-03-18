package com.app.quantitymeasurement.service;

import com.app.quantitymeasurement.dto.*;
import com.app.quantitymeasurement.entity.QuantityMeasurementEntity;
import com.app.quantitymeasurement.model.*;

import java.util.List;

public interface IQuantityMeasurementService {


    QuantityMeasurementEntity convert(QuantityInputDTO input);

    QuantityMeasurementEntity add(QuantityInputDTO input);

    List<QuantityMeasurementEntity> getHistoryByOperation(String operation);

    long getOperationCount(String operation);

	QuantityMeasurementEntity compare(QuantityInputDTO input);

	
}
