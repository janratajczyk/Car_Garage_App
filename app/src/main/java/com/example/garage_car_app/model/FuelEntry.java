package com.example.garage_car_app.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "fuel_entries")
public class FuelEntry {

    @PrimaryKey(autoGenerate = true)
    public int id;

    public int carId;

    public String date;

    public double liters;

    public double cost;

    public double mileage;

    public FuelEntry(int carId, String date, double liters, double cost, double mileage) {
        this.carId = carId;
        this.date = date;
        this.liters = liters;
        this.cost = cost;
        this.mileage = mileage;
    }
}
