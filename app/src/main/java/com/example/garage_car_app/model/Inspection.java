package com.example.garage_car_app.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.ColumnInfo;

@Entity
public class Inspection {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private int carId;
    private String date;
    private String notes;

    public Inspection(int carId, String date, String notes) {
        this.carId = carId;
        this.date = date;
        this.notes = notes;
    }

    public int getId() { return id; }
    public int getCarId() { return carId; }
    public String getDate() { return date; }
    public String getNotes() { return notes; }

    public void setId(int id) { this.id = id; }
    public void setCarId(int carId) { this.carId = carId; }
    public void setDate(String date) { this.date = date; }
    public void setNotes(String notes) { this.notes = notes; }
}

