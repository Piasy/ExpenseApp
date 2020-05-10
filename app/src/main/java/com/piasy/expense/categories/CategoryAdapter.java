package com.piasy.expense.categories;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.piasy.expense.R;
import com.piasy.expense.model.Category;
import com.piasy.expense.model.Transaction;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by Piasy{github.com/Piasy} on 2020/5/9.
 */
public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {

    private final Listener mListener;
    private final List<CategoryItem> mCategoryItems = new ArrayList<>();

    public CategoryAdapter(final Listener listener) {
        mListener = listener;
    }

    public void showCategories(List<CategoryItem> categoryItems) {
        mCategoryItems.clear();
        mCategoryItems.addAll(categoryItems);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull final ViewGroup viewGroup, final int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_category, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int i) {
        CategoryItem category = mCategoryItems.get(i);

        viewHolder.mContainer.setBackgroundColor(Color.parseColor(category.getCategory().color()));
        viewHolder.mAlert.setVisibility(
                category.getBudget() < category.getTotalExpense() ? View.VISIBLE : View.GONE);
        viewHolder.mName.setText(category.getCategory().name());
        viewHolder.mExpense.setText(
                String.format(Locale.ENGLISH, "%.2f / %.2f %s", category.getTotalExpense(),
                        category.getBudget(), Transaction.currencyName(category.getCurrency())));

        viewHolder.mContainer.setOnClickListener(
                v -> mListener.editCategory(category.getCategory()));
    }

    @Override
    public int getItemCount() {
        return mCategoryItems.size();
    }

    public interface Listener {
        void editCategory(Category category);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        final LinearLayout mContainer;
        final ImageView mAlert;
        final TextView mName;
        final TextView mExpense;

        public ViewHolder(@NonNull final View itemView) {
            super(itemView);

            mContainer = itemView.findViewById(R.id.container);
            mAlert = itemView.findViewById(R.id.alert);
            mName = itemView.findViewById(R.id.name);
            mExpense = itemView.findViewById(R.id.expense);
        }
    }
}
