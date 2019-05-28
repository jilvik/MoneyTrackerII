package com.example.moneytracker;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class AddItemActivity extends AppCompatActivity {

    private EditText newName;
    private EditText newPrice;
    private Button addButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);
        setTitle(R.string.add_item_title);

        newName = findViewById(R.id.new_name);
        newPrice = findViewById(R.id.new_price);
        addButton = findViewById(R.id.add_button);

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
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = newName.getText().toString();
                String price = newPrice.getText().toString();
            }
        });
    }
}
