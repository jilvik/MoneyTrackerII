package com.example.moneytracker;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

class BudgetAdapter extends RecyclerView.Adapter<BudgetAdapter.RecordViewHolder> {

    private List<Record> data = new ArrayList<>();

    public void setData(List<Record> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    public void addItem(Record record) {
        data.add(0, record);
        notifyItemInserted(0);
    }

    @NonNull
    @Override
    public RecordViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.record, parent, false);
        return new RecordViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecordViewHolder viewHolder, int position) {
        Record record = data.get(position);
        viewHolder.applyData(record);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    static class RecordViewHolder extends RecyclerView.ViewHolder {

        private final TextView name;
        private final TextView price;

        RecordViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            price = itemView.findViewById(R.id.price);
        }

        void applyData(Record record) {
            name.setText(record.getName());
            price.setText(record.getPrice());
        }
    }

}


