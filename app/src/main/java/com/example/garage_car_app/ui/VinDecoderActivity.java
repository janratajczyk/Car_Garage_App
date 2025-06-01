package com.example.garage_car_app.ui;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.garage_car_app.R;
import com.example.garage_car_app.api.ApiClient;
import com.example.garage_car_app.api.NhtsaApiService;
import com.example.garage_car_app.model.NhtsaResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VinDecoderActivity extends AppCompatActivity {

    private EditText vinInput;
    private Button decodeButton;
    private TextView brandView, modelView, yearView;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_car);

        vinInput = findViewById(R.id.edit_text_vin);
        decodeButton = findViewById(R.id.button_fetch_vin);
        brandView = findViewById(R.id.editTextBrand);
        modelView = findViewById(R.id.editTextModel);
        yearView = findViewById(R.id.editTextYear);

        decodeButton.setOnClickListener(v -> {
            String vin = vinInput.getText().toString().trim();
            if (!vin.isEmpty()) {
                decodeVin(vin);
            }
        });
    }

    private void decodeVin(String vin) {
        NhtsaApiService apiService = ApiClient.getClient().create(NhtsaApiService.class);
        Call<NhtsaResponse> call = apiService.decodeVin(vin, "json");

        call.enqueue(new Callback<NhtsaResponse>() {
            @Override
            public void onResponse(Call<NhtsaResponse> call, Response<NhtsaResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    String make = "", model = "", year = "";
                    for (NhtsaResponse.Result result : response.body().getResults()) {
                        switch (result.getVariable()) {
                            case "Make": make = result.getValue(); break;
                            case "Model": model = result.getValue(); break;
                            case "Model Year": year = result.getValue(); break;
                        }
                    }
                    brandView.setText("Marka: " + make);
                    modelView.setText("Model: " + model);
                    yearView.setText("Rok: " + year);
                }
            }

            @Override
            public void onFailure(Call<NhtsaResponse> call, Throwable t) {
                Toast.makeText(VinDecoderActivity.this, "Błąd: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
