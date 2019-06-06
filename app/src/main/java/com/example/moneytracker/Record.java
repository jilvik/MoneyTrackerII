package com.example.moneytracker;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
class Record {

    public static final String TYPE_UNKNOWN = "unknown";
    public static final String TYPE_INCOME = "incomes";
    public static final String TYPE_EXPENSE = "expenses";
    public static final String TYPE_BUDGET = "budget";

    private int id;
    private String name;
    private int price;
    private String type;
}
