package com.piasy.expense.transactions;

import com.piasy.expense.model.Category;
import com.piasy.expense.model.Transaction;

/**
 * Created by Piasy{github.com/Piasy} on 2020/5/9.
 */
public class TransactionItem {
    private final Transaction mTransaction;
    private final Category mCategory;
    private final double mExchangeToNzd;

    public TransactionItem(final Transaction transaction,
            final Category category, final double exchangeToNzd) {
        mTransaction = transaction;
        mCategory = category;
        mExchangeToNzd = exchangeToNzd;
    }

    public Transaction getTransaction() {
        return mTransaction;
    }

    public Category getCategory() {
        return mCategory;
    }

    public double getExchangeToNzd() {
        return mExchangeToNzd;
    }

    @Override
    public String toString() {
        return "TransactionItem{" +
               "mTransaction=" + mTransaction +
               ", mCategory=" + mCategory +
               ", mExchangeToNzd=" + mExchangeToNzd +
               '}';
    }
}
