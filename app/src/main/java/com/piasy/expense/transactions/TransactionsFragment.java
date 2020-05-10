package com.piasy.expense.transactions;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.afollestad.materialdialogs.MaterialDialog;
import com.github.piasy.yamvp.dagger2.YaMvpDiFragment;
import com.piasy.expense.MainActionsHandler;
import com.piasy.expense.R;
import com.piasy.expense.di.ExpenseComponent;
import com.piasy.expense.model.Category;
import com.piasy.expense.model.Transaction;
import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.Subject;
import java.util.List;

/**
 * Created by Piasy{github.com/Piasy} on 2020/5/9.
 */
public class TransactionsFragment
        extends YaMvpDiFragment<TransactionsView, TransactionsPresenter, ExpenseComponent>
        implements TransactionsView, MainActionsHandler {

    private static final String TAG = "TransactionsFragment";

    private final Subject<Boolean> mAddActions = PublishSubject.create();
    private final Subject<Transaction> mAddOrUpdateTransaction = PublishSubject.create();
    private final Subject<Category> mAddOrUpdateCategory = PublishSubject.create();

    private TextView mCreateCategoryTip;
    private RecyclerView mTransactions;
    private TransactionAdapter mTransactionAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater,
            @Nullable final ViewGroup container,
            @Nullable final Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_transactions, container, false);
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mCreateCategoryTip = view.findViewById(R.id.create_category);
        mCreateCategoryTip.setOnClickListener(v -> mAddActions.onNext(true));

        mTransactions = view.findViewById(R.id.transactions);
        mTransactionAdapter = new TransactionAdapter();
        mTransactions.setLayoutManager(new LinearLayoutManager(getContext()));
        mTransactions.setAdapter(mTransactionAdapter);
    }

    @Override
    protected void injectDependencies(final ExpenseComponent component) {
        component.inject(this);
    }

    @Override
    public Observable<Transaction> transactionChanges() {
        return mAddOrUpdateTransaction;
    }

    @Override
    public Observable<Category> categoryChanges() {
        return mAddOrUpdateCategory;
    }

    @Override
    public Observable<Boolean> addActions() {
        return mAddActions;
    }

    @Override
    public void showAddCategoryTip() {
        Log.d(TAG, "showAddCategoryTip");
        mTransactions.setVisibility(View.GONE);
        mCreateCategoryTip.setVisibility(View.VISIBLE);
    }

    @Override
    public void showTransactions(final List<TransactionItem> transactions) {
        Log.d(TAG, "showTransactions " + transactions);
        mTransactions.setVisibility(View.VISIBLE);
        mCreateCategoryTip.setVisibility(View.GONE);

        mTransactionAdapter.showTransactions(transactions);
    }

    @Override
    public void showCreateTransaction(List<Category> categories) {
        Log.d(TAG, "showCreateTransaction");
    }

    @Override
    public void showCreateCategory() {
        Log.d(TAG, "showCreateCategory");
    }

    @Override
    public void onAdd() {
        mAddActions.onNext(true);
    }
}
