package com.piasy.expense.categories;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.piasy.expense.R;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Piasy{github.com/Piasy} on 2020/5/9.
 */
public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {

    private final List<CategoryItem> mCategoryItems = new ArrayList<>();

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

    }

    @Override
    public int getItemCount() {
        return mCategoryItems.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(@NonNull final View itemView) {
            super(itemView);
        }
    }
}
