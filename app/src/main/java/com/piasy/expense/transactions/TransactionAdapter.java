package com.piasy.expense.transactions;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;
import com.piasy.expense.R;
import com.piasy.expense.model.Transaction;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import org.threeten.bp.format.DateTimeFormatter;

/**
 * Created by Piasy{github.com/Piasy} on 2020/5/9.
 */
public class TransactionAdapter extends RecyclerView.Adapter<TransactionAdapter.ViewHolder> {

    private final Listener mListener;
    private final DateTimeFormatter mDateTimeFormatter;
    private final List<TransactionItem> mTransactionItems = new ArrayList<>();

    public TransactionAdapter(
            final Listener listener,
            final DateTimeFormatter dateTimeFormatter) {
        mListener = listener;
        mDateTimeFormatter = dateTimeFormatter;
    }

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
        TransactionItem transaction = mTransactionItems.get(i);

        viewHolder.mContainer.setBackgroundColor(
                Color.parseColor(transaction.getCategory().color()));
        viewHolder.mDate.setText(transaction.getTransaction().date().format(mDateTimeFormatter));
        viewHolder.mCategory.setText(transaction.getCategory().name());
        viewHolder.mAmount.setText(String.format(Locale.ENGLISH, "%.02f NZD",
                transaction.getTransaction().amount() * transaction.getExchangeToNzd()));

        viewHolder.mContainer.setOnClickListener(
                v -> mListener.editTransaction(transaction.getTransaction()));
    }

    @Override
    public int getItemCount() {
        return mTransactionItems.size();
    }

    public interface Listener {
        void editTransaction(Transaction transaction);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        final FrameLayout mContainer;
        final TextView mDate;
        final TextView mCategory;
        final TextView mAmount;

        public ViewHolder(@NonNull final View itemView) {
            super(itemView);

            mContainer = itemView.findViewById(R.id.container);
            mDate = itemView.findViewById(R.id.date);
            mCategory = itemView.findViewById(R.id.category);
            mAmount = itemView.findViewById(R.id.amount);
        }
    }
}
