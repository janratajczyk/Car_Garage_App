package com.example.garage_car_app.model;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface FuelEntryDao {

    @Insert
    void insert(FuelEntry entry);

    @Query("SELECT * FROM fuel_entries WHERE carId = :carId ORDER BY date DESC")
    List<FuelEntry> getAllForCar(int carId);

    @Delete
    void delete(FuelEntry entry);

    @Update
    void update(FuelEntry entry);

    @Query("SELECT AVG(liters) FROM fuel_entries WHERE carId = :carId")
    Double getAverageConsumption(int carId);
}
