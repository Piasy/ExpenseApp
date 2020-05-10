package com.piasy.expense.model;

import android.database.sqlite.SQLiteStatement;
import android.support.annotation.Nullable;
import com.google.auto.value.AutoValue;
import com.piasy.expense.CategoryModel;

import static com.piasy.expense.model.Transaction.CURRENCY_NZD;

/**
 * Created by Piasy{github.com/Piasy} on 2020/5/9.
 */
@AutoValue
public abstract class Category implements CategoryModel {

    public static final Category UNKNOWN = create(-1L, "Unknown", "#cccccc", Long.MAX_VALUE,
            CURRENCY_NZD);

    static Category create(Long id, String name, String color, double budget,
            int currency) {
        return builder()
                .id(id)
                .name(name)
                .color(color)
                .budget(budget)
                .currency(currency)
                .build();
    }

    public static Builder builder() {
        return new AutoValue_Category.Builder();
    }

    SQLiteStatement insert(final InsertCategory insert) {
        insert.program.clearBindings();
        insert.bind(id(), name(), color(), budget(), currency());
        return insert.program;
    }

    @AutoValue.Builder
    public static abstract class Builder {
        public abstract Builder id(@Nullable Long id);

        public abstract Builder name(String name);

        public abstract Builder color(String color);

        public abstract Builder budget(double budget);

        public abstract Builder currency(int currency);

        public abstract Category build();
    }
}
