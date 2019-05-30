package com.example.moneytracker;

import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;


public class BudgetActivity extends AppCompatActivity {

    private static final int VERTICAL_SPACE = 32;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_budget);
        setTitle(R.string.budget_title);

        BudgetAdapter adapter = new BudgetAdapter();

        RecyclerView recycler = findViewById(R.id.list);;
        recycler.setLayoutManager(new LinearLayoutManager(this));


        recycler.addItemDecoration(new VerticalSpaceItemDecoration(VERTICAL_SPACE));
        recycler.setAdapter(adapter);
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
        }
    }
}
