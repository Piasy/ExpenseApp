package com.piasy.expense.categories;

import com.piasy.expense.model.Category;

/**
 * Created by Piasy{github.com/Piasy} on 2020/5/9.
 */
public class CategoryItem {
    private final Category mCategory;
    private final double mTotalExpense;
    private final double mBudget;
    private final int mCurrency;

    public CategoryItem(final Category category, final double totalExpense, final double budget,
            final int currency) {
        mCategory = category;
        mTotalExpense = totalExpense;
        mBudget = budget;
        mCurrency = currency;
    }

    public Category getCategory() {
        return mCategory;
    }

    public double getTotalExpense() {
        return mTotalExpense;
    }

    public double getBudget() {
        return mBudget;
    }

    public int getCurrency() {
        return mCurrency;
    }

    @Override
    public String toString() {
        return "CategoryItem{" +
               "mCategory=" + mCategory +
               ", mTotalExpense=" + mTotalExpense +
               ", mBudget=" + mBudget +
               ", mCurrency=" + mCurrency +
               '}';
    }
}
