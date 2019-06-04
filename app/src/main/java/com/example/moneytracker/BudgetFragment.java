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

    static final int TYPE_INCOME = 1;
    static final int TYPE_EXPENSE = 2;
    static final int TYPE_BUDGET = 3;
    private static final String TYPE_KEY = "type";
    private static final int TYPE_UNKNOWN = -1;
    private static final int VERTICAL_SPACE = 32;
    private BudgetAdapter adapter;

    static BudgetFragment createBudgetFragment(int type) {
        BudgetFragment fragment = new BudgetFragment();

        Bundle bundle = new Bundle();
        bundle.putInt(BudgetFragment.TYPE_KEY, BudgetFragment.TYPE_INCOME);

        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter = new BudgetAdapter();

        Bundle bundle = getArguments();
        int type = bundle.getInt(TYPE_KEY, TYPE_UNKNOWN);

        if (type == TYPE_UNKNOWN) {
            throw new IllegalArgumentException("UNKNOWN TYPE!");
        }
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
        recycler.setAdapter(adapter);
    }
}
