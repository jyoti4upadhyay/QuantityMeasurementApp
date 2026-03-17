package com.measurementApp.repository;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.measurementApp.entity.QuantityMeasurementEntity;
import com.measurementApp.units.ConnectionUtil;

public class QuantityMeasurementDBRepository implements IQuantityMeasurementRepository {

    @Override
    public void save(QuantityMeasurementEntity entity) {

        try {

            Connection conn = ConnectionUtil.getConnection();

            String sql = "INSERT INTO quantity_measurement(operation,input1,input2,result) VALUES(?,?,?,?)";

            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setString(1, entity.getOperation());
            ps.setString(2, entity.getInput1());
            ps.setString(3, entity.getInput2());
            ps.setString(4, entity.getResult());

            ps.executeUpdate();

            System.out.println("Operation stored in DB");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<QuantityMeasurementEntity> findAll() {

        List<QuantityMeasurementEntity> list = new ArrayList<>();

        try {

            Connection conn = ConnectionUtil.getConnection();

            String sql = "SELECT * FROM quantity_measurement";

            PreparedStatement ps = conn.prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                QuantityMeasurementEntity entity =
                        new QuantityMeasurementEntity(
                                rs.getString("operation"),
                                rs.getString("input1"),
                                rs.getString("input2"),
                                rs.getString("result")
                        );

                list.add(entity);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }
}