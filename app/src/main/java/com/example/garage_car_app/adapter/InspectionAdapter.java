package com.example.garage_car_app.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.garage_car_app.R;
import com.example.garage_car_app.model.Inspection;

import java.util.List;

public class InspectionAdapter extends RecyclerView.Adapter<InspectionAdapter.ViewHolder> {
    private List<Inspection> inspectionList;

    public InspectionAdapter(List<Inspection> list) {
        this.inspectionList = list;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textDate, textNotes;

        public ViewHolder(View view) {
            super(view);
            textDate = view.findViewById(R.id.textInspectionDate);
            textNotes = view.findViewById(R.id.textInspectionNotes);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_inspection, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Inspection inspection = inspectionList.get(position);
        holder.textDate.setText("Data: " + inspection.getDate());
        holder.textNotes.setText("Notatki: " + inspection.getNotes());
    }

    @Override
    public int getItemCount() {
        return inspectionList.size();
    }
}

