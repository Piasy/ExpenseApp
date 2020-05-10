package com.piasy.expense.categories;

import android.util.Log;
import com.github.piasy.yamvp.rx.YaRxPresenter;
import com.piasy.expense.model.Category;
import com.piasy.expense.model.ExchangeRepo;
import com.piasy.expense.model.ExpenseRepo;
import com.piasy.expense.model.Transaction;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.BehaviorSubject;
import io.reactivex.subjects.Subject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.inject.Inject;

/**
 * Created by Piasy{github.com/Piasy} on 2020/5/9.
 */
class CategoriesPresenter extends YaRxPresenter<CategoriesView> {

    private static final String TAG = "CategoriesPresenter";

    private final ExpenseRepo mExpenseRepo;
    private final ExchangeRepo mExchangeRepo;

    // default as NZD
    private final Subject<Integer> mCurrency = BehaviorSubject.createDefault(
            Transaction.CURRENCY_NZD);

    @Inject
    CategoriesPresenter(final ExpenseRepo expenseRepo,
            final ExchangeRepo exchangeRepo) {
        mExpenseRepo = expenseRepo;
        mExchangeRepo = exchangeRepo;
    }

    @Override
    public void attachView(final CategoriesView view) {
        super.attachView(view);

        Log.d(TAG, "attachView");

        listenCurrencyChanges();

        listenCategoryChanges();

        showCategories();
    }

    private void listenCurrencyChanges() {
        addUtilDestroy(getView().currencyChanges()
                .observeOn(Schedulers.computation())
                .subscribe(mCurrency::onNext, Throwable::printStackTrace));
    }

    private void listenCategoryChanges() {
        addUtilDestroy(getView().categoryChanges()
                .observeOn(Schedulers.io())
                .subscribe(mExpenseRepo::addOrUpdateCategory, Throwable::printStackTrace));
    }

    private void showCategories() {
        addUtilDestroy(
                Observable.combineLatest(mExpenseRepo.categories(), mExpenseRepo.transactions(),
                        mCurrency, mExchangeRepo.exchangeUsdNzd(),
                        this::transformToCategoryItems)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(categories -> {
                            if (isViewAttached()) {
                                if (categories.isEmpty()) {
                                    getView().showAddCategoryTip();
                                } else {
                                    getView().showCategories(categories);
                                }
                            }
                        }, Throwable::printStackTrace));
    }

    private List<CategoryItem> transformToCategoryItems(List<Category> categories,
            List<Transaction> transactions, int currency, Double exchange) {
        Log.d(TAG, "transformToCategoryItems "
                   + categories.size() + " categories, "
                   + transactions.size() + " transactions, "
                   + " currency " + currency + ", exchange " + exchange);

        Map<Long, Double> expenses = new HashMap<>();
        for (Transaction transaction : transactions) {
            Double expense = expenses.get(transaction.category());
            if (expense == null) {
                expense = 0D;
            }
            if (transaction.currency() == currency) {
                expense += transaction.amount();
            } else if (transaction.currency() == Transaction.CURRENCY_NZD) {
                expense += transaction.amount() / exchange;
            } else {
                expense += transaction.amount() * exchange;
            }
            expenses.put(transaction.category(), expense);
        }

        List<CategoryItem> categoryItems = new ArrayList<>(categories.size());
        for (Category category : categories) {
            Double expense = expenses.get(category.id());
            if (expense == null) {
                expense = 0D;
            }
            double budget;
            if (category.currency() == currency) {
                budget = category.budget();
            } else if (category.currency() == Transaction.CURRENCY_NZD) {
                budget = category.budget() / exchange;
            } else {
                budget = category.budget() * exchange;
            }
            categoryItems.add(new CategoryItem(category, expense, budget, currency));
        }

        return categoryItems;
    }
}
