package com.example.garage_car_app.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.garage_car_app.R;
import com.example.garage_car_app.model.AppDatabase;
import com.example.garage_car_app.model.Car;
import com.example.garage_car_app.model.AppDatabase;
import com.example.garage_car_app.ui.CarDetailsActivity;

import java.util.List;

public class CarAdapter extends RecyclerView.Adapter<CarAdapter.CarViewHolder> {

    private Context context;
    private List<Car> carList;

    public CarAdapter(Context context, List<Car> carList) {
        this.context = context;
        this.carList = carList;
    }

    @NonNull
    @Override
    public CarViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_car, parent, false);
        return new CarViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CarViewHolder holder, int position) {
        Car car = carList.get(position);
        holder.textViewBrand.setText(car.getBrand());
        holder.textViewModel.setText(car.getModel());
        holder.textViewYear.setText(String.valueOf(car.getYear()));
        holder.textViewRegistration.setText(car.getRegistration());

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, CarDetailsActivity.class);
            intent.putExtra("brand", car.getBrand());
            intent.putExtra("model", car.getModel());
            intent.putExtra("year", car.getYear());
            intent.putExtra("registration", car.getRegistration());
            context.startActivity(intent);
        });

        holder.btnDelete.setOnClickListener(v -> {
            new AlertDialog.Builder(context)
                    .setTitle("Usuń pojazd")
                    .setMessage("Czy na pewno chcesz usunąć ten pojazd?")
                    .setPositiveButton("Tak", (dialog, which) -> {
                        Car carToDelete = carList.get(position);
                        new Thread(() -> {
                            AppDatabase.getInstance(context).carDao().deleteCar(carToDelete);
                            ((Activity) context).runOnUiThread(() -> {
                                carList.remove(position);
                                notifyItemRemoved(position);
                            });
                        }).start();
                    })
                    .setNegativeButton("Nie", null)
                    .show();
        });
    }

    @Override
    public int getItemCount() {
        return carList.size();
    }

    public static class CarViewHolder extends RecyclerView.ViewHolder {
        TextView textViewBrand, textViewModel, textViewYear, textViewRegistration;
        Button btnDelete;

        public CarViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewBrand = itemView.findViewById(R.id.textViewBrand);
            textViewModel = itemView.findViewById(R.id.textViewModel);
            textViewYear = itemView.findViewById(R.id.textViewYear);
            textViewRegistration = itemView.findViewById(R.id.textViewRegistration);
            btnDelete = itemView.findViewById(R.id.btnDelete);
        }
    }
}
