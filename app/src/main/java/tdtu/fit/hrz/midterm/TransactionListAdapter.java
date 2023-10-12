package tdtu.fit.hrz.midterm;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import tdtu.fit.hrz.midterm.entity.TransactionRequest;
import tdtu.fit.hrz.midterm.entity.Transaction;
import tdtu.fit.hrz.midterm.entity.TransactionCategory;

/**
 *
 */
public class TransactionListAdapter extends RecyclerView.Adapter<TransactionListAdapter.TransactionViewHolder> {
    private static ArrayList<Transaction> mTransactionList;
    private final LayoutInflater mInflater;
    private Context context;
    private int resourceID;
    public TransactionListAdapter(Context context, ArrayList<Transaction> transactions, int resourceID){
        this.context = context;
        this.mInflater = LayoutInflater.from(context);
        mTransactionList = transactions;
        this.resourceID = resourceID;
    }
    @NonNull
    @Override
    public TransactionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mItemView = mInflater.inflate(this.resourceID,parent, false);
        return new TransactionViewHolder(mItemView, this);
    }

    @Override
    public void onBindViewHolder(@NonNull TransactionViewHolder holder, int position) {
        Transaction transaction = mTransactionList.get(position);
        holder.update(transaction);
    }

    @Override
    public int getItemCount() {
        return mTransactionList.size();
    }

    public class TransactionViewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener {
        final TransactionListAdapter mAdapter;
        Transaction transaction;
        ImageView icon;
        TextView transaction_cate;
        TextView transaction_amount;
        TextView transaction_date;
        TextView transaction_currency;

        public TransactionViewHolder(@NonNull View itemView, TransactionListAdapter transactionListAdapter) {
            super(itemView);
            mAdapter = transactionListAdapter;
            transaction_cate = itemView.findViewById(R.id.transaction_category);
            transaction_amount = itemView.findViewById(R.id.transaction_amount);
            transaction_date = itemView.findViewById(R.id.transaction_date);
            transaction_currency = itemView.findViewById(R.id.transaction_currency);
            itemView.setOnLongClickListener(this);
        }

        public void update(Transaction transaction) {
            this.transaction = transaction;
            transaction_cate.setText(String.format("%s", transaction.getCategory()));
            transaction_amount.setText(
                    String.format("%s", transaction.getSpentAmountString()));
            transaction_currency.setText(
                    String.format("%s", transaction.getCurrency().getCurrencyCode()));
            transaction_date.setText(String.format("%s", transaction.getSpentDateString()));

            if (TransactionCategory.INCOME_GIFT.equals(transaction.getCategory())
                    || TransactionCategory.INCOME_SALARY.equals(transaction.getCategory())){
                transaction_amount.setTextColor(context.getResources().getColor(R.color.green));
            } else transaction_amount.setTextColor(context.getResources().getColor(R.color.dark_red));

        }

        @Override
        public boolean onLongClick(View view) {
            Intent displayIntent = new Intent(context, TransactionDetailActivity.class);
            Bundle bundle = new Bundle();
            bundle.putInt("transaction_id", transaction.getTransactionId());
            displayIntent.setAction(TransactionRequest.DISPLAY.getAction());
            displayIntent.putExtras(bundle);

            context.startActivity(displayIntent);
            return true;
        }
    }
}
