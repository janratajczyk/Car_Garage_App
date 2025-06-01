package com.example.garage_car_app.model;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface CarDao {

    @Insert
    void insert(Car car);

    @Query("SELECT * FROM car WHERE id = :carId")
    Car getCarById(int carId);

    @Query("SELECT * FROM Car")
    List<Car> getAllCars();

    @Delete
    void deleteCar(Car car);

    @Query("DELETE FROM car WHERE registration = :registration")
    void deleteCarByRegistration(String registration);

}
