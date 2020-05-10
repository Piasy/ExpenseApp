package com.piasy.expense.categories;

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
import com.github.piasy.yamvp.dagger2.YaMvpDiFragment;
import com.piasy.expense.MainActionsHandler;
import com.piasy.expense.R;
import com.piasy.expense.di.ExpenseComponent;
import com.piasy.expense.model.Category;
import io.reactivex.Observable;
import java.util.List;

/**
 * Created by Piasy{github.com/Piasy} on 2020/5/9.
 */
public class CategoriesFragment
        extends YaMvpDiFragment<CategoriesView, CategoriesPresenter, ExpenseComponent>
        implements CategoriesView, MainActionsHandler {

    private static final String TAG = "CategoriesFragment";

    private TextView mCreateCategoryTip;
    private RecyclerView mCategories;
    private CategoryAdapter mCategoryAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater,
            @Nullable final ViewGroup container,
            @Nullable final Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_categories, container, false);
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mCreateCategoryTip = view.findViewById(R.id.create_category);
        mCategories = view.findViewById(R.id.categories);

        mCategoryAdapter = new CategoryAdapter();
        mCategories.setLayoutManager(new LinearLayoutManager(getContext()));
        mCategories.setAdapter(mCategoryAdapter);
    }

    @Override
    protected void injectDependencies(final ExpenseComponent component) {
        component.inject(this);
    }

    @Override
    public Observable<Integer> currencyChanges() {
        return Observable.empty();
    }

    @Override
    public Observable<Category> categoryChanges() {
        return Observable.empty();
    }

    @Override
    public void showAddCategoryTip() {
        Log.d(TAG, "showAddCategoryTip");
        mCategories.setVisibility(View.GONE);
        mCreateCategoryTip.setVisibility(View.VISIBLE);
    }

    @Override
    public void showCategories(final List<CategoryItem> categories) {
        Log.d(TAG, "showCategories " + categories);
        mCategories.setVisibility(View.VISIBLE);
        mCreateCategoryTip.setVisibility(View.GONE);

        mCategoryAdapter.showCategories(categories);
    }

    @Override
    public void onAdd() {
        Log.d(TAG, "onAdd");
    }
}
