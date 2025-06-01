package com.example.garage_car_app.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.garage_car_app.api.ApiClient;
import com.example.garage_car_app.api.NhtsaApiService;

import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import com.example.garage_car_app.R;
import com.example.garage_car_app.model.AppDatabase;
import com.example.garage_car_app.model.Car;
import com.example.garage_car_app.model.NhtsaResponse;

public class AddCarActivity extends AppCompatActivity {

    private EditText editTextBrand, editTextModel, editTextYear, editTextRegistration;

    EditText editTextVin, editTextMake;
    Button buttonFetchVin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_car);

        editTextVin = findViewById(R.id.edit_text_vin);
        editTextMake = findViewById(R.id.editTextBrand);
        editTextModel = findViewById(R.id.editTextModel);
        editTextYear = findViewById(R.id.editTextYear);
        buttonFetchVin = findViewById(R.id.button_fetch_vin);

        buttonFetchVin.setOnClickListener(v -> {
            String vin = editTextVin.getText().toString().trim();
            if (!vin.isEmpty()) {
                fetchCarDataFromVin(vin);
            } else {
                Toast.makeText(this, "Wpisz VIN", Toast.LENGTH_SHORT).show();
            }
        });


        editTextBrand = findViewById(R.id.editTextBrand);
        editTextModel = findViewById(R.id.editTextModel);
        editTextYear = findViewById(R.id.editTextYear);
        editTextRegistration = findViewById(R.id.editTextRegistration);
        Button buttonSaveCar = findViewById(R.id.buttonSaveCar);

        buttonSaveCar.setOnClickListener(v -> {
            String brand = editTextBrand.getText().toString();
            String model = editTextModel.getText().toString();
            String yearStr = editTextYear.getText().toString();
            String registration = editTextRegistration.getText().toString();

            if (brand.isEmpty() || model.isEmpty() || yearStr.isEmpty() || registration.isEmpty()) {
                Toast.makeText(this, "Wypełnij wszystkie pola", Toast.LENGTH_SHORT).show();
                return;
            }

            int year = Integer.parseInt(yearStr);

            // Zapisz do bazy danych
            AppDatabase db = AppDatabase.getInstance(getApplicationContext());
            Car car = new Car(brand, model, year, registration);
            db.carDao().insert(car);

            Toast.makeText(this, "Dodano auto!", Toast.LENGTH_SHORT).show();
            finish();
        });

    }
    private void fetchCarDataFromVin(String vin) {
        NhtsaApiService apiService = ApiClient.getClient().create(NhtsaApiService.class);
        Call<NhtsaResponse> call = apiService.decodeVin(vin, "json");

        call.enqueue(new Callback<NhtsaResponse>() {
            @Override
            public void onResponse(Call<NhtsaResponse> call, Response<NhtsaResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    String make = "", model = "", year = "";
                    for (NhtsaResponse.Result result : response.body().getResults()) {
                        String variable = result.getVariable();
                        String value = result.getValue();
                        Log.d("VIN_DECODE", "Variable: " + variable + ", Value: " + value);

                        switch (variable) {
                            case "Make": make = value; break;
                            case "Model": model = value; break;
                            case "Model Year": year = value; break;
                        }
                    }

                    editTextMake.setText(make);
                    editTextModel.setText(model);
                    editTextYear.setText(year);
                } else {
                    Toast.makeText(AddCarActivity.this, "Brak danych", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<NhtsaResponse> call, Throwable t) {
                Toast.makeText(AddCarActivity.this, "Błąd: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}
