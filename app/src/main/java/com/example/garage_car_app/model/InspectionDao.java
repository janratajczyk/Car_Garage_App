package com.example.garage_car_app.model;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface InspectionDao {
    @Query("DELETE FROM inspection WHERE carId = :carId")
    void deleteInspectionsForCar(int carId);

    @Query("SELECT * FROM Inspection WHERE carId = :carId ORDER BY date DESC")
    List<Inspection> getInspectionsForCar(int carId);

    @Insert
    void insert(Inspection inspection);
    @Update
    void update(Inspection inspection);
    @Delete
    void delete(Inspection inspection);
}
