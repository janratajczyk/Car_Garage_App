package com.example.garage_car_app.ui;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import com.example.garage_car_app.R;
import com.example.garage_car_app.model.AppDatabase;
import com.example.garage_car_app.model.FuelEntry;

public class AddFuelEntryActivity extends AppCompatActivity {
    private EditText etDate, etLiters, etCost, etMileage;
    private Button btnSave;
    private int carId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_fuel_entry);

        carId = getIntent().getIntExtra("carId", -1);

        etDate = findViewById(R.id.etDate);
        etLiters = findViewById(R.id.etLiters);
        etCost = findViewById(R.id.etCost);
        etMileage = findViewById(R.id.etMileage);
        btnSave = findViewById(R.id.btnSaveFuel);

        btnSave.setOnClickListener(v -> {
            String date = etDate.getText().toString();
            double liters = Double.parseDouble(etLiters.getText().toString());
            double cost = Double.parseDouble(etCost.getText().toString());
            double mileage = Double.parseDouble(etMileage.getText().toString());

            FuelEntry entry = new FuelEntry(carId, date, liters, cost, mileage);
            AppDatabase db = Room.databaseBuilder(getApplicationContext(),
                    AppDatabase.class, "car_database").allowMainThreadQueries().build();

            db.fuelEntryDao().insert(entry);
            finish();
        });
    }
}
