package com.example.garage_car_app.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Repair {
    @PrimaryKey(autoGenerate = true)
    public int id;

    public int carId;
    public String description;
    public String date;
    public double cost;

    public Repair(int carId, String description, String date, double cost) {
        this.carId = carId;
        this.description = description;
        this.date = date;
        this.cost = cost;
    }
}
