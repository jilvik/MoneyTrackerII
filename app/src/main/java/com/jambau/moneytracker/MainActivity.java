package com.jambau.moneytracker;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import static com.jambau.moneytracker.BudgetFragment.ADD_ITEM_REQUEST_CODE;

public class MainActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener{

    private TabLayout tabLayout;
    private ViewPager pager;
    private FloatingActionButton floatButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.budget_tool_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.budget_title);

        pager = findViewById(R.id.pager);
        tabLayout = findViewById(R.id.tab_layout);

        pager.addOnPageChangeListener(this);

        tabLayout.setupWithViewPager(pager);

        floatButton = findViewById(R.id.fab);
        floatButton.setOnClickListener(v -> {

            int currentPage = pager.getCurrentItem();
            String type = null;

            if (currentPage == MainAdapter.PAGE_INCOMES) {
                type = Record.TYPE_INCOME;
            } else if (currentPage == MainAdapter.PAGE_EXPENSES) {
                type = Record.TYPE_EXPENSE;
            }

            Intent intent = new Intent(MainActivity.this, AddItemActivity.class);
            intent.putExtra(AddItemActivity.TYPE_KEY, type);
            startActivityForResult(intent, ADD_ITEM_REQUEST_CODE);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (((App) getApplication()).isAuthorized()) {
            initTabs();
        } else {
            Intent intent = new Intent(this, AuthActivity.class);
            startActivity(intent);
        }
    }

    @Override
    public void onPageScrolled(int i, float v, int i1) {

    }

    @Override
    public void onPageSelected(int position) {
        switch (position) {
            case MainAdapter.PAGE_INCOMES:
            case MainAdapter.PAGE_EXPENSES:
                floatButton.show();
                break;
            case MainAdapter.PAGE_BALANCE:
                floatButton.hide();
                break;
        }
    }

    @Override
    public void onPageScrollStateChanged(int i) {
        switch (i) {
            case ViewPager.SCROLL_STATE_IDLE:
                floatButton.setEnabled(true);
                break;
            case ViewPager.SCROLL_STATE_DRAGGING:
            case ViewPager.SCROLL_STATE_SETTLING:
                floatButton.setEnabled(false);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        for (Fragment fragment : getSupportFragmentManager().getFragments()) {
            fragment.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void initTabs() {
        MainAdapter adapter = new MainAdapter(getSupportFragmentManager(), this);
        pager.setAdapter(adapter);
    }
}
