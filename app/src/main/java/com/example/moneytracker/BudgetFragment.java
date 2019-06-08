package com.example.moneytracker;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.List;


public class BudgetFragment extends Fragment {

    private static final String TYPE_KEY = "type";
    static final int ADD_ITEM_REQUEST_CODE = 123;
    private BudgetAdapter adapter;
    private Api api;
    private String type;
    private SwipeRefreshLayout refresh;

    static BudgetFragment createBudgetFragment(String type) {
        BudgetFragment fragment = new BudgetFragment();

        Bundle bundle = new Bundle();
        bundle.putString(BudgetFragment.TYPE_KEY, type);
        bundle.putBoolean("key", true);

        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter = new BudgetAdapter();

        Bundle bundle = getArguments();
        type = bundle.getString(TYPE_KEY, Record.TYPE_EXPENSE);

        if (type.equals(Record.TYPE_UNKNOWN)) {
            throw new IllegalArgumentException("UNKNOWN TYPE!");
        }

        api = ((App) getActivity().getApplication()).getApi();
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
        refresh = view.findViewById(R.id.refresh);

        recycler.setLayoutManager(new LinearLayoutManager(getContext()));
        recycler.setAdapter(adapter);

        refresh.setColorSchemeColors(Color.BLUE, Color.CYAN, Color.GREEN);
        refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadItems();
            }
        });

        loadItems();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == ADD_ITEM_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            Record record = data.getParcelableExtra("record");
            if (record.getType().equals(type)) {
                adapter.addItem(record);
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    private void loadItems() {
        Call<List<Record>> call = api.getItems(type);

        call.enqueue(new Callback<List<Record>>() {
            @Override
            public void onResponse(Call<List<Record>> call, Response<List<Record>> response) {

                adapter.setData(response.body());
                refresh.setRefreshing(false);
            }

            @Override
            public void onFailure(Call<List<Record>> call, Throwable t) {
                refresh.setRefreshing(false);
            }
        });
    }
}
