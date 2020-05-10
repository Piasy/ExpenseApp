package com.piasy.expense.transactions;

import com.github.piasy.yamvp.YaView;
import com.piasy.expense.model.Category;
import com.piasy.expense.model.Transaction;
import io.reactivex.Observable;
import java.util.List;

/**
 * Created by Piasy{github.com/Piasy} on 2020/5/9.
 */
interface TransactionsView extends YaView {
    Observable<Transaction> transactionChanges();

    Observable<Category> categoryChanges();

    Observable<Boolean> addActions();

    void showAddCategoryTip();

    void showTransactions(List<TransactionItem> transactions);

    void showCreateTransaction(List<Category> categories);

    void showCreateCategory();
}
