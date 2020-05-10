package com.piasy.expense.di;

import com.piasy.expense.categories.CategoriesFragment;
import com.piasy.expense.transactions.TransactionsFragment;
import dagger.Component;
import javax.inject.Singleton;

/**
 * Created by Piasy{github.com/Piasy} on 2020/5/9.
 */
@Singleton
@Component(modules = {
        ProviderModule.class
})
public interface ExpenseComponent {
    void inject(TransactionsFragment fragment);

    void inject(CategoriesFragment fragment);
}
