package com.example.moneytracker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;


public class AddItemActivity extends AppCompatActivity {

    public static final String TYPE_KEY = "type";

    private EditText newName;
    private EditText newPrice;
    private Button addButton;
    private String type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);
        Toolbar toolbar = findViewById(R.id.add_item_tool_bar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.add_item_title);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        newName = findViewById(R.id.new_name);
        newPrice = findViewById(R.id.new_price);
        addButton = findViewById(R.id.add_button);
        type = getIntent().getStringExtra(TYPE_KEY);

        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String name = newName.getText().toString();
                String price = newPrice.getText().toString();
                addButton.setEnabled(!name.isEmpty()  &&  !price.isEmpty());
            }
        };

        newName.addTextChangedListener(textWatcher);
        newPrice.addTextChangedListener(textWatcher);

        /*
          Get value from "name" and "price" fields after click button
         */
        addButton.setOnClickListener(v -> {
            String name = newName.getText().toString();
            String price = newPrice.getText().toString();

            Record record = new Record(name, price, type);

            Intent intent = new Intent();
            intent.putExtra("record", record);

            setResult(RESULT_OK, intent);
            finish();
        });
    }
}
