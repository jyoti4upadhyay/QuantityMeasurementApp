package com.quantitymeasurementservice.repository;

import com.quantitymeasurementservice.entity.QuantityMeasurementEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuantityMeasurementRepository extends JpaRepository<QuantityMeasurementEntity, Long> {

    List<QuantityMeasurementEntity> findAllByOrderByIdDesc();
}
