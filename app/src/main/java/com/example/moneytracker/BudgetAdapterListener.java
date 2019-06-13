package com.example.moneytracker;

public interface BudgetAdapterListener {
    void onItemClick(Record record, int position);
    void onItemLongClick(Record record, int position);
}
