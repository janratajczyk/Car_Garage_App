package com.example.garage_car_app.ui;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.LayoutInflater;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.garage_car_app.R;

import com.example.garage_car_app.adapter.InspectionAdapter;
import com.example.garage_car_app.adapter.RepairAdapter;
import com.example.garage_car_app.model.AppDatabase;
import com.example.garage_car_app.model.Car;
import com.example.garage_car_app.model.Inspection;
import com.example.garage_car_app.model.Repair;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.List;

public class CarDetailsActivity extends AppCompatActivity {
    TextView textBrand, textModel, textYear, textRegistration;
    RecyclerView recyclerRepairs, recyclerInspections;

    AppDatabase db;
    int carId;

    private RepairAdapter repairAdapter;
    private InspectionAdapter inspectionAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_details);

        textBrand = findViewById(R.id.textBrand);
        textModel = findViewById(R.id.textModel);
        textYear = findViewById(R.id.textYear);
        textRegistration = findViewById(R.id.textRegistration);

        recyclerRepairs = findViewById(R.id.recyclerRepairs);
        recyclerInspections = findViewById(R.id.recyclerInspections);

        recyclerRepairs.setLayoutManager(new LinearLayoutManager(this));
        recyclerInspections.setLayoutManager(new LinearLayoutManager(this));

        carId = getIntent().getIntExtra("carId", -1);
        String brand = getIntent().getStringExtra("brand");
        String model = getIntent().getStringExtra("model");
        int year = getIntent().getIntExtra("year", 0);
        String registration = getIntent().getStringExtra("registration");

        textBrand.setText("Marka: " + brand);
        textModel.setText("Model: " + model);
        textYear.setText("Rok: " + year);
        textRegistration.setText("Rejestracja: " + registration);

        db = AppDatabase.getInstance(this);

        List<Repair> repairs = db.repairDao().getRepairsForCar(carId);
        List<Inspection> inspections = db.inspectionDao().getInspectionsForCar(carId);

        repairAdapter = new RepairAdapter(repairs);
        recyclerRepairs.setAdapter(repairAdapter);

        inspectionAdapter = new InspectionAdapter(inspections);
        recyclerInspections.setAdapter(inspectionAdapter);

        Button buttonAddRepair = findViewById(R.id.buttonAddRepair);
        buttonAddRepair.setOnClickListener(view -> showAddRepairDialog());

        Button buttonAddInspection = findViewById(R.id.buttonAddInspection);
        buttonAddInspection.setOnClickListener(v -> showAddInspectionDialog());

        Button buttonFuelHistory = findViewById(R.id.buttonFuelHistory);
        buttonFuelHistory.setOnClickListener(v -> {
            Intent intent = new Intent(CarDetailsActivity.this, FuelHistoryActivity.class);
            intent.putExtra("carId", carId);
            startActivity(intent);
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void showAddRepairDialog() {
        BottomSheetDialog dialog = new BottomSheetDialog(this);
        View view = LayoutInflater.from(this).inflate(R.layout.bottom_sheet_add_repair, null);
        dialog.setContentView(view);

        EditText editDescription = view.findViewById(R.id.editDescription);
        EditText editDate = view.findViewById(R.id.editDate);
        EditText editCost = view.findViewById(R.id.editCost);
        Button buttonSave = view.findViewById(R.id.buttonSaveRepair);

        buttonSave.setOnClickListener(v -> {
            String description = editDescription.getText().toString();
            String date = editDate.getText().toString();
            double cost;

            try {
                cost = Double.parseDouble(editCost.getText().toString());
            } catch (NumberFormatException e) {
                Toast.makeText(this, "Niepoprawny koszt", Toast.LENGTH_SHORT).show();
                return;
            }

            Repair repair = new Repair(carId, description, date, cost);
            db.repairDao().insert(repair);
            List<Repair> updatedRepairs = db.repairDao().getRepairsForCar(carId);
            repairAdapter.updateData(updatedRepairs);

            dialog.dismiss();
        });

        dialog.show();
    }

    private void showAddInspectionDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Dodaj przegląd");

        View view = getLayoutInflater().inflate(R.layout.dialog_add_inspection, null);
        EditText editDate = view.findViewById(R.id.editInspectionDate);
        EditText editNotes = view.findViewById(R.id.editInspectionNotes);

        builder.setView(view);
        builder.setPositiveButton("Zapisz", (dialog, which) -> {
            String date = editDate.getText().toString();
            String notes = editNotes.getText().toString();

            if (!date.isEmpty()) {
                Inspection inspection = new Inspection(carId, date, notes);
                db.inspectionDao().insert(inspection);
                List<Inspection> updated = db.inspectionDao().getInspectionsForCar(carId);
                inspectionAdapter = new InspectionAdapter(updated);
                recyclerInspections.setAdapter(inspectionAdapter);
            } else {
                Toast.makeText(this, "Data jest wymagana", Toast.LENGTH_SHORT).show();
            }
        });

        builder.setNegativeButton("Anuluj", null);
        builder.show();
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}