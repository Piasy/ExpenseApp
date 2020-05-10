package com.piasy.expense.model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.NonNull;
import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by Piasy{github.com/Piasy} on 2020/5/9.
 */
@Singleton
public class DbOpenHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "Expense_db";

    private static final int VERSION = 1;

    @Inject
    DbOpenHelper(@NonNull final Context context) {
        super(context, DB_NAME, null, VERSION);
    }

    @Override
    public void onCreate(final SQLiteDatabase db) {
        db.execSQL(Transaction.CREATE_TABLE);
        db.execSQL(Category.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(final SQLiteDatabase db, final int oldVersion, final int newVersion) {
        // no impl
    }
}
