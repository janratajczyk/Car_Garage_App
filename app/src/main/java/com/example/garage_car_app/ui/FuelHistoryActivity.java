package com.example.garage_car_app.ui;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.LinearLayout;
import com.example.garage_car_app.model.FuelEntry;
import com.example.garage_car_app.model.AppDatabase;
import androidx.room.Room;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import com.example.garage_car_app.R;
import java.util.List;

public class FuelHistoryActivity extends AppCompatActivity {
    private TextView tvAverage;
    private LinearLayout listContainer;
    private int carId;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fuel_history);

        tvAverage = findViewById(R.id.tvAverageConsumption);
        listContainer = findViewById(R.id.fuelListContainer);
        carId = getIntent().getIntExtra("carId", -1);
        if (carId == -1) {
            Toast.makeText(this, "Nieprawidłowe ID auta", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }
        AppDatabase db = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "car_database").allowMainThreadQueries().build();

        List<FuelEntry> entries = db.fuelEntryDao().getAllForCar(carId);
        Double avg = db.fuelEntryDao().getAverageConsumption(carId);

        tvAverage.setText("Średnie spalanie: " + (avg != null ? String.format("%.2f", avg) : "N/A") + " l/100km");

        for (FuelEntry e : entries) {
            TextView tv = new TextView(this);
            tv.setText(e.date + " - " + e.liters + "L, " + e.mileage + "km");
            listContainer.addView(tv);
        }
    }
}