package com.example.garage_car_app.model;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface RepairDao {

    @Insert
    void insert(Repair repair);

    @Query("SELECT * FROM Repair WHERE carId = :carId ORDER BY date DESC")
    List<Repair> getRepairsForCar(int carId);
}
