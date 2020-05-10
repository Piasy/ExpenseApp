package com.piasy.expense.transactions;

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
public class TransactionAdapter extends RecyclerView.Adapter<TransactionAdapter.ViewHolder> {

    private final List<TransactionItem> mTransactionItems = new ArrayList<>();

    public void showTransactions(List<TransactionItem> transactionItems) {
        mTransactionItems.clear();
        mTransactionItems.addAll(transactionItems);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull final ViewGroup viewGroup, final int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_transaction, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int i) {

    }

    @Override
    public int getItemCount() {
        return mTransactionItems.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(@NonNull final View itemView) {
            super(itemView);
        }
    }
}
