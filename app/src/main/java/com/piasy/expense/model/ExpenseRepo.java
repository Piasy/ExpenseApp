package com.piasy.expense.model;

import com.squareup.sqlbrite.BriteDatabase;
import com.squareup.sqldelight.RowMapper;
import hu.akarnokd.rxjava.interop.RxJavaInterop;
import io.reactivex.Observable;
import java.util.List;
import javax.inject.Inject;
import org.threeten.bp.format.DateTimeFormatter;

/**
 * Created by Piasy{github.com/Piasy} on 2020/5/9.
 */
public class ExpenseRepo {
    private final BriteDatabase mDatabase;

    private final RowMapper<Category> mCategoryMapper;
    private final Category.InsertCategory mInsertCategory;

    private final RowMapper<Transaction> mTransactionMapper;
    private final Transaction.InsertTransaction mInsertTransaction;

    @Inject
    ExpenseRepo(final BriteDatabase database, final DateTimeFormatter dateTimeFormatter) {
        mDatabase = database;

        final Category.Factory<Category> categoryFactory = new Category.Factory<>(Category::create);

        mCategoryMapper = categoryFactory.select_allMapper();
        mInsertCategory = new Category.InsertCategory(mDatabase.getWritableDatabase());

        final Transaction.Factory<Transaction> transactionFactory = new Transaction.Factory<>(
                Transaction::create, new ZonedDateTimeDelightAdapter(dateTimeFormatter));

        mTransactionMapper = transactionFactory.select_allMapper();
        mInsertTransaction = new Transaction.InsertTransaction(mDatabase.getWritableDatabase(),
                transactionFactory);
    }

    public Observable<List<Category>> categories() {
        return RxJavaInterop.toV2Observable(
                mDatabase.createQuery(Category.TABLE_NAME, Category.SELECT_ALL)
                        .mapToList(mCategoryMapper::map));
    }

    public void addOrUpdateCategory(Category category) {
        mDatabase.executeInsert(Category.TABLE_NAME, category.insert(mInsertCategory));
    }

    public Observable<List<Transaction>> transactions() {
        return RxJavaInterop.toV2Observable(
                mDatabase.createQuery(Transaction.TABLE_NAME, Transaction.SELECT_ALL)
                        .mapToList(mTransactionMapper::map));
    }

    public void addOrUpdateTransaction(Transaction transaction) {
        mDatabase.executeInsert(Transaction.TABLE_NAME, transaction.insert(mInsertTransaction));
    }
}
