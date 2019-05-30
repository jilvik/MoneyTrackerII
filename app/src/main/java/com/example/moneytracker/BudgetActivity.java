package com.example.moneytracker;

import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class BudgetActivity extends AppCompatActivity {

    private List<Record> data = new ArrayList<>();
    private final int VERTICAL_SPACE = 32;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_budget);
        setTitle(R.string.budget_title);

        RecyclerView recyclerView = findViewById(R.id.list);;
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        BudgetAdapter adapter = new BudgetAdapter();
        createData();
        recyclerView.addItemDecoration(new VerticalSpaceItemDecoration(VERTICAL_SPACE));
        recyclerView.setAdapter(adapter);
    }

    private void createData() {
        data.add(new Record("Milk", 53));
        data.add(new Record("Soap", 100));
        data.add(new Record("ToothPaste", 300));
        data.add(new Record("bread", 20));
        data.add(new Record("Any veeeeeeeeeeeeeeeeeeeeeeeery long record", 1000000000));
        data.add(new Record("Chocolate", 65));
        data.add(new Record("Pizza", 400));
        data.add(new Record("Cafe", 1500));
        data.add(new Record("Rocket", 900));
        data.add(new Record("Course", 1400));
        data.add(new Record("Tea", 50));
        data.add(new Record("Coffee", 700));
    }

    private class BudgetAdapter extends RecyclerView.Adapter<RecordViewHolder> {

        @NonNull
        @Override
        public RecordViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_record, parent, false);
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
    }

    private class RecordViewHolder extends RecyclerView.ViewHolder {

        private final TextView NAME;
        private final TextView PRICE;

        RecordViewHolder(@NonNull View itemView) {
            super(itemView);
            NAME = itemView.findViewById(R.id.name);
            PRICE = itemView.findViewById(R.id.price);
        }

        void applyData(Record record) {
            NAME.setText(record.getNAME());
            PRICE.setText(String.valueOf(record.getPRICE()));
        }
    }

    private class VerticalSpaceItemDecoration extends RecyclerView.ItemDecoration {

        private final int spaceHeight;

        private VerticalSpaceItemDecoration(int spaceHeight) {
            this.spaceHeight = spaceHeight;
        }

        @Override
        public void getItemOffsets(@NonNull Rect outRect,
                                   @NonNull View view,
                                   @NonNull RecyclerView parent,
                                   @NonNull RecyclerView.State state) {
            outRect.top = spaceHeight / 2;
            outRect.bottom = spaceHeight / 2;
            outRect.left = spaceHeight;
            outRect.right = spaceHeight;
        }
    }
}
