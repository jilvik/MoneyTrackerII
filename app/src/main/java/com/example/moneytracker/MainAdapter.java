package com.example.moneytracker;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class MainAdapter extends FragmentPagerAdapter {

    private static final int PAGE_INCOMES = 0;
    private static final int PAGE_EXPENSES = 1;
    private static final int PAGE_BALANCE = 2;

    private String[] titles;

    MainAdapter(FragmentManager fragmentManager, Context context) {

        super(fragmentManager);
        titles = context.getResources().getStringArray(R.array.tab_title);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case PAGE_INCOMES:
                return BudgetFragment.createBudgetFragment(Record.TYPE_INCOME);

            case PAGE_EXPENSES:
                return BudgetFragment.createBudgetFragment(Record.TYPE_EXPENSE);

            case PAGE_BALANCE:
                return BudgetFragment.createBudgetFragment(Record.TYPE_BUDGET);

            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }
}
