package com.example.garage_car_app.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.garage_car_app.R;
import com.example.garage_car_app.adapter.CarAdapter;
import com.example.garage_car_app.model.AppDatabase;
import com.example.garage_car_app.model.Car;
import com.example.garage_car_app.model.CarDao;
import com.example.garage_car_app.util.ReminderScheduler;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.room.Room;

import java.util.List;

public class CarListActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private CarAdapter carAdapter;
    private List<Car> carList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_list);

        ReminderScheduler.scheduleReminder(this);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));



        AppDatabase db = Room.databaseBuilder(getApplicationContext(),
                        AppDatabase.class, "car_db")
                .fallbackToDestructiveMigration()
                .allowMainThreadQueries()
                .build();

        CarDao carDao = db.carDao();



        carList = carDao.getAllCars();


        carAdapter = new CarAdapter(this, carList);
        recyclerView.setAdapter(carAdapter);


        FloatingActionButton fab = findViewById(R.id.fabAddCar);
        fab.setOnClickListener(view -> {
            Intent intent = new Intent(CarListActivity.this, AddCarActivity.class);
            startActivity(intent);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();


        AppDatabase db = Room.databaseBuilder(getApplicationContext(),
                        AppDatabase.class, "car_database")
                .fallbackToDestructiveMigration()
                .allowMainThreadQueries()
                .build();

        CarDao carDao = db.carDao();

        carList.clear();
        carList.addAll(carDao.getAllCars());
        carAdapter.notifyDataSetChanged();
    }
}
