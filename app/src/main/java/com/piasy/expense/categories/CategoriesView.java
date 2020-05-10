package com.piasy.expense.categories;

import com.github.piasy.yamvp.YaView;
import com.piasy.expense.model.Category;
import io.reactivex.Observable;
import java.util.List;

/**
 * Created by Piasy{github.com/Piasy} on 2020/5/9.
 */
interface CategoriesView extends YaView {
    Observable<Integer> currencyChanges();

    Observable<Category> categoryChanges();

    void showAddCategoryTip();

    void showCategories(List<CategoryItem> categories);
}
