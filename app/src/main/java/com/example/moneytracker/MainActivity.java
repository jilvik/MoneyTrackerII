package com.example.moneytracker;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

public class MainActivity extends AppCompatActivity {

    private ViewPager pager;
    private TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.budget_tool_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.budget_title);

        pager = findViewById(R.id.pager);
        tabLayout = findViewById(R.id.tab_layout);

        MainAdapter adapter = new MainAdapter(getSupportFragmentManager(), this);
        pager.setAdapter(adapter);

        tabLayout.setupWithViewPager(pager);
    }
}
