package com.piasy.expense;

import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import com.piasy.expense.categories.CategoriesFragment;
import com.piasy.expense.stats.StatsFragment;
import com.piasy.expense.transactions.TransactionsFragment;

/**
 * Created by Piasy{github.com/Piasy} on 2020/5/9.
 */
public enum MainScreen {
    STATS(R.id.bottom_navigation_item_stats, new StatsFragment()),
    TRANSACTIONS(R.id.bottom_navigation_item_transactions, new TransactionsFragment()),
    CATEGORIES(R.id.bottom_navigation_item_categories, new CategoriesFragment());

    @IdRes
    private int menuId;
    private Fragment fragment;

    MainScreen(@IdRes int menuId, Fragment fragment) {
        this.menuId = menuId;
        this.fragment = fragment;
    }

    public static MainScreen getMainScreenForMenuItem(@IdRes int menuId) {
        for (MainScreen screen : MainScreen.values()) {
            if (screen.menuId == menuId) {
                return screen;
            }
        }
        return null;
    }

    @IdRes
    public int menuId() {
        return menuId;
    }

    public Fragment fragment() {
        return fragment;
    }
}
