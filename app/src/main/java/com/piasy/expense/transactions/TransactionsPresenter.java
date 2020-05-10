package com.piasy.expense.transactions;

import android.util.Log;
import android.util.Pair;
import com.github.piasy.yamvp.rx.YaRxPresenter;
import com.piasy.expense.model.Category;
import com.piasy.expense.model.ExchangeRepo;
import com.piasy.expense.model.ExpenseRepo;
import com.piasy.expense.model.Transaction;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.inject.Inject;

/**
 * Created by Piasy{github.com/Piasy} on 2020/5/9.
 */
class TransactionsPresenter extends YaRxPresenter<TransactionsView> {

    private static final String TAG = "TransactionsPresenter";

    private final ExpenseRepo mExpenseRepo;
    private final ExchangeRepo mExchangeRepo;

    private final List<Category> mLatestCats = new ArrayList<>();

    @Inject
    TransactionsPresenter(final ExpenseRepo expenseRepo,
            final ExchangeRepo exchangeRepo) {
        mExpenseRepo = expenseRepo;
        mExchangeRepo = exchangeRepo;
    }

    @Override
    public void attachView(final TransactionsView view) {
        super.attachView(view);

        Log.d(TAG, "attachView");

        listenAddActions();
        listenCategoryChanges();
        listenTransactionChanges();

        showTransactions();
    }

    private void listenAddActions() {
        addUtilDestroy(getView().addActions()
                .subscribe(categories -> {
                    if (mLatestCats.isEmpty()) {
                        getView().showCreateCategory();
                    } else {
                        getView().showCreateTransaction(mLatestCats);
                    }
                }, Throwable::printStackTrace));
    }

    private void listenTransactionChanges() {
        addUtilDestroy(getView().transactionChanges()
                .observeOn(Schedulers.io())
                .subscribe(mExpenseRepo::addOrUpdateTransaction, Throwable::printStackTrace));
    }

    private void listenCategoryChanges() {
        addUtilDestroy(getView().categoryChanges()
                .observeOn(Schedulers.io())
                .subscribe(mExpenseRepo::addOrUpdateCategory, Throwable::printStackTrace));
    }

    private void showTransactions() {
        addUtilDestroy(
                Observable.combineLatest(mExpenseRepo.categories(), mExpenseRepo.transactions(),
                        mExchangeRepo.exchangeUsdNzd(),
                        this::transformToTransactionItem)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(pair -> {
                            if (isViewAttached()) {
                                if (pair.second.isEmpty() && pair.first == 0) {
                                    getView().showAddCategoryTip();
                                } else {
                                    getView().showTransactions(pair.second);
                                }
                            }
                        }, Throwable::printStackTrace));
    }

    private Pair<Integer, List<TransactionItem>> transformToTransactionItem(
            List<Category> categories, List<Transaction> transactions, Double exchange) {
        Log.d(TAG, "transformToTransactionItem "
                   + categories.size() + " categories, "
                   + transactions.size() + " transactions, exchange " + exchange);
        List<TransactionItem> transactionItems = new ArrayList<>();

        mLatestCats.clear();
        mLatestCats.addAll(categories);

        Map<Long, Category> categoryMap = new HashMap<>();
        for (Category category : categories) {
            Long id = category.id();
            if (id != null) {
                categoryMap.put(id, category);
            }
        }

        for (Transaction transaction : transactions) {
            Category category = categoryMap.get(transaction.category());
            if (category == null) {
                category = Category.UNKNOWN;
            }
            transactionItems.add(new TransactionItem(transaction, category,
                    transaction.currency() == Transaction.CURRENCY_NZD ? 1 : exchange));
        }

        return Pair.create(categories.size(), transactionItems);
    }
}
