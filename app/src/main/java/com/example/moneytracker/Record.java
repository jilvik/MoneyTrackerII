package com.example.moneytracker;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
class Record {

    private final String NAME;
    private final int PRICE;
    private String comment;
}
