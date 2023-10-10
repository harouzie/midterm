package tdtu.fit.hrz.midterm;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.LinkedList;

import tdtu.fit.hrz.midterm.entity.Transaction;

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

    public class TransactionViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        final TransactionListAdapter mAdapter;
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
            itemView.setOnClickListener(this);
        }

        public void update(Transaction transaction) {
            transaction_cate.setText(String.format("%s", transaction.getCategory()));
            transaction_amount.setText(String.format("%d", transaction.getSpentAmount()));
            transaction_currency.setText(String.format("%s", transaction.getCurrency().getCurrencyCode()));
            transaction_date.setText(String.format("%s", transaction.getSpentDateString()));
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            Toast.makeText(
                view.getContext(), mTransactionList.get(position).toString(), Toast.LENGTH_SHORT)
                .show();
        }
    }
}
