package com.example.moneytracker;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

class BudgetAdapter extends RecyclerView.Adapter<BudgetAdapter.RecordViewHolder> {

    private List<Record> data = new ArrayList<>();
    private BudgetAdapterListener listener = null;
    private SparseBooleanArray selections = new SparseBooleanArray();

    public void setData(List<Record> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    public void addItem(Record record) {
        data.add(0, record);
        notifyItemInserted(0);
    }

    public void setListener(BudgetAdapterListener listener) {
        this.listener = listener;
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
        viewHolder.applyData(record, position, listener, selections.get(position, false));
    }

    @Override
    public int getItemCount() {

        return data.size();
    }

    public void toggleSelection(int position) {
        if (selections.get(position, false)) {
            selections.delete(position);
        } else {
            selections.put(position, true);
        }
        notifyItemChanged(position);
    }

    void clearSelections() {
        selections.clear();
        notifyDataSetChanged();
    }

    int getSelectedItemCount() {
        return selections.size();
    }

    List<Integer> getSelectedItems() {
        List<Integer> items = new ArrayList<>(selections.size());
        for (int i = 0; i < selections.size(); i++) {
            items.add(selections.keyAt(i));
        }
        return items;
    }

    Record remove(int pos) {
        final Record record = data.remove(pos);
        notifyItemRemoved(pos);
        return record;
    }

    static class RecordViewHolder extends RecyclerView.ViewHolder {

        private final TextView name;
        private final TextView price;

        RecordViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            price = itemView.findViewById(R.id.price);
        }

        void applyData(final Record record, final int position, final BudgetAdapterListener listener, boolean selected) {
            name.setText(record.getName());
            price.setText(record.getPrice());

            itemView.setOnClickListener(v -> {
                if (listener != null) {
                    listener.onItemClick(record, position);
                }
            });

            itemView.setOnLongClickListener(v -> {
                if (listener != null) {
                    listener.onItemLongClick(record, position);
                }
                return true;
            });

            itemView.setActivated(selected);
        }
    }
}


