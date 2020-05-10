package com.piasy.expense.utils;

import com.piasy.expense.model.Category;
import com.piasy.expense.model.Transaction;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import org.threeten.bp.ZonedDateTime;

/**
 * Created by Piasy{github.com/Piasy} on 2020/5/10.
 */
public class TestUtil {
    private static final Random RANDOM = new Random(System.currentTimeMillis());

    private static final List<Category> CATS = Arrays.asList(
            Category.builder()
                    .id(100L)
                    .name("Transport")
                    .color("#29b6f6")
                    .budget(100)
                    .currency(Transaction.CURRENCY_NZD)
                    .build(),
            Category.builder()
                    .id(101L)
                    .name("Foods")
                    .color("#f06292")
                    .budget(500)
                    .currency(Transaction.CURRENCY_NZD)
                    .build(),
            Category.builder()
                    .id(102L)
                    .name("Shopping")
                    .color("#ef6f77")
                    .budget(500)
                    .currency(Transaction.CURRENCY_NZD)
                    .build(),
            Category.builder()
                    .id(103L)
                    .name("Learning")
                    .color("#efe5a1")
                    .budget(1000)
                    .currency(Transaction.CURRENCY_NZD)
                    .build()
    );

    private static int sCatsIndex = 0;
    private static boolean sAllCatsAdded = false;

    private TestUtil() {
        // no instance
    }

    public static Category nextCategory() {
        if (sCatsIndex == CATS.size() - 1) {
            sAllCatsAdded = true;
        }
        sCatsIndex = (sCatsIndex + 1) % CATS.size();
        return CATS.get(sCatsIndex);
    }

    public static Transaction randomTransaction() {
        Category cat = CATS.get(RANDOM.nextInt(Integer.MAX_VALUE)
                                % ((sAllCatsAdded && sCatsIndex > 0) ? CATS.size() : sCatsIndex));
        return Transaction.builder()
                .amount(RANDOM.nextInt((int) cat.budget()))
                .category(cat.id())
                .currency(
                        RANDOM.nextBoolean() ? Transaction.CURRENCY_NZD : Transaction.CURRENCY_USD)
                .date(ZonedDateTime.now())
                .build();
    }
}
