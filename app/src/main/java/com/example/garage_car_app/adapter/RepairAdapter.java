package com.example.garage_car_app.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.garage_car_app.R;
import com.example.garage_car_app.model.Repair;
import java.util.List;

public class RepairAdapter extends RecyclerView.Adapter<RepairAdapter.RepairViewHolder> {

    private List<Repair> repairs;

    public RepairAdapter(List<Repair> repairs) {
        this.repairs = repairs;
    }

    @NonNull
    @Override
    public RepairViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_repair, parent, false);
        return new RepairViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RepairViewHolder holder, int position) {
        Repair repair = repairs.get(position);
        holder.textDescription.setText(repair.description);
        holder.textDate.setText(repair.date);
        holder.textCost.setText(String.format("%.2f zł", repair.cost));
    }

    @Override
    public int getItemCount() {
        return repairs.size();
    }

    public void updateData(List<Repair> newRepairs) {
        this.repairs = newRepairs;
        notifyDataSetChanged();
    }

    static class RepairViewHolder extends RecyclerView.ViewHolder {
        TextView textDescription, textDate, textCost;

        public RepairViewHolder(@NonNull View itemView) {
            super(itemView);
            textDescription = itemView.findViewById(R.id.textRepairDescription);
            textDate = itemView.findViewById(R.id.textRepairDate);
            textCost = itemView.findViewById(R.id.textRepairCost);
        }
    }
}
