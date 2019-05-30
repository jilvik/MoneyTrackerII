package com.example.moneytracker;

import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class BudgetFragment extends Fragment {

    private static final int VERTICAL_SPACE = 32;
    private BudgetAdapter adapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter = new BudgetAdapter();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_budget, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView recycler = view.findViewById(R.id.list);

        recycler.setLayoutManager(new LinearLayoutManager(getContext()));
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
