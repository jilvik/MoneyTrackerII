package com.jambau.moneytracker;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.*;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.List;


public class BudgetFragment extends Fragment {

    private static final String TYPE_KEY = "type";
    static final int ADD_ITEM_REQUEST_CODE = 123;
    private BudgetAdapter adapter;
    private Api api;
    private App app;
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
        adapter.setListener(new AdapterListener());

        Bundle bundle = getArguments();
        type = bundle.getString(TYPE_KEY, Record.TYPE_EXPENSE);

        if (type.equals(Record.TYPE_UNKNOWN)) {
            throw new IllegalArgumentException("UNKNOWN TYPE!");
        }

        app = (App) getActivity().getApplication();
        api = app.getApi();
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
        refresh.setOnRefreshListener(() -> loadItems());

        loadItems();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == ADD_ITEM_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            Record record = data.getParcelableExtra("record");
            if (record.getType().equals(type)) {
                addItem(record);
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    private void loadItems() {
        Call<List<Record>> call = api.getItems(type, app.getAuthToken());

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

    private void addItem(final Record record) {
        Call<AddItemResult> call = api.addItem(record.getPrice(), record.getName(), record.getType(), app.getAuthToken());

        call.enqueue(new Callback<AddItemResult>() {
            @Override
            public void onResponse(Call<AddItemResult> call, Response<AddItemResult> response) {
                AddItemResult result = response.body();
                if (result.getStatus().equals("success")) {
                    record.setId(result.getId());
                    adapter.addItem(record);
                }
            }

            @Override
            public void onFailure(Call<AddItemResult> call, Throwable t) {

            }
        });
    }

    /*   ACTION MODE AREA   */

    private ActionMode actionMode = null;

    void removeSelectedItems() {
        for (int i = adapter.getSelectedItems().size() - 1; i >= 0; i--) {
            adapter.remove(adapter.getSelectedItems().get(i));
        }

        actionMode.finish();
    }

    private class AdapterListener implements BudgetAdapterListener {

        @Override
        public void onItemClick(Record item, int position) {
            if (isInActionMode()) {
                toggleSelection(position);
            }
        }

        @Override
        public void onItemLongClick(Record item, int position) {
            if (isInActionMode()) {
                return;
            }

            actionMode = ((AppCompatActivity) getActivity()).startSupportActionMode(actionModeCallback);
            toggleSelection(position);
        }

        private boolean isInActionMode() {
            return actionMode != null;
        }

        private void toggleSelection(int position) {
            adapter.toggleSelection(position);
        }
    }

    private ActionMode.Callback actionModeCallback = new ActionMode.Callback() {
        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            MenuInflater inflater = new MenuInflater(getContext());
            inflater.inflate(R.menu.items_menu, menu);
            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false;
        }

        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {

            switch (item.getItemId()) {
                case R.id.remove:
                    //removeSelectedItems();
                    showDialog();

                    break;
            }

            return false;
        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {
            adapter.clearSelections();
            actionMode = null;
        }
    };

    private void showDialog() {
        ConfirmationDialog dialog = new ConfirmationDialog();
        dialog.setTargetFragment(this, 1);
        dialog.show(getFragmentManager(), "ConfirmationDialog");
    }
}
