package com.example.garage_car_app.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Car {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String brand;
    private String model;
    private int year;
    public String registration;

    public Car(String brand, String model, int year, String registration) {
        this.brand = brand;
        this.model = model;
        this.year = year;
        this.registration = registration;
    }


    public int getId() {
        return id;
    }

    public String getBrand() {
        return brand;
    }

    public String getModel() {
        return model;
    }

    public int getYear() {
        return year;
    }
    public String getRegistration() {
        return registration;
    }


    public void setId(int id) {
        this.id = id;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public void setYear(int year) {
        this.year = year;
    }
    public void setRegistration(String registration) {
        this.registration = registration;
    }
}
