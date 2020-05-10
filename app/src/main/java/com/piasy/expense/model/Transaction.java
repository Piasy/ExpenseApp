package com.piasy.expense.model;

import android.database.sqlite.SQLiteStatement;
import android.support.annotation.Nullable;
import com.google.auto.value.AutoValue;
import com.piasy.expense.TransactionModel;
import org.threeten.bp.ZonedDateTime;

/**
 * Created by Piasy{github.com/Piasy} on 2020/5/9.
 */
@AutoValue
public abstract class Transaction implements TransactionModel {

    public static final int CURRENCY_USD = 1;
    public static final int CURRENCY_NZD = 2;

    static Transaction create(Long id, double amount, long category, int currency,
            ZonedDateTime date) {
        return builder()
                .id(id)
                .amount(amount)
                .category(category)
                .currency(currency)
                .date(date)
                .build();
    }

    public static String currencyName(int currency) {
        switch (currency) {
            case CURRENCY_NZD:
                return "NZD";
            case CURRENCY_USD:
                return "USD";
            default:
                return "UNKNOWN";
        }
    }

    public static Builder builder() {
        return new AutoValue_Transaction.Builder();
    }

    SQLiteStatement insert(final InsertTransaction insert) {
        insert.program.clearBindings();
        insert.bind(id(), amount(), category(), currency(), date());
        return insert.program;
    }

    @AutoValue.Builder
    public static abstract class Builder {
        public abstract Builder id(@Nullable Long id);

        public abstract Builder amount(double amount);

        public abstract Builder category(long category);

        public abstract Builder currency(int currency);

        public abstract Builder date(ZonedDateTime date);

        public abstract Transaction build();
    }
}
