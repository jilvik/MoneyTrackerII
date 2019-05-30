package com.example.moneytracker;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
class Record {

    private final String name;
    private final int price;
    private String comment;
}
