package tdtu.fit.hrz.midterm;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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
    public TransactionListAdapter(Context context, ArrayList<Transaction> transactions){
        this.context = context;
        this.mInflater = LayoutInflater.from(context);
        mTransactionList = transactions;
    }
    @NonNull
    @Override
    public TransactionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mItemView = mInflater.inflate(R.layout.transactionlist_item,parent, false);
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
        TextView money_row;
        TextView category_row;
        TextView currency_row;
        TextView date_row;


        public TransactionViewHolder(@NonNull View itemView, TransactionListAdapter transactionListAdapter) {
            super(itemView);
            mAdapter = transactionListAdapter;
            money_row = itemView.findViewById(R.id.money_row);
            category_row = itemView.findViewById(R.id.category_row);
            currency_row = itemView.findViewById(R.id.currency_row);
            date_row = itemView.findViewById(R.id.date_row);
            itemView.setOnClickListener(this);
        }

        public void update(Transaction transaction) {
            money_row.setText(String.format("%.2f", transaction.getSpentAmount()));
            category_row.setText(String.format("%s", transaction.getCategory()));
            currency_row.setText(String.format("%s", transaction.getCurrency().getCurrencyCode()));
            date_row.setText(String.format("%s", transaction.getSpentDate().toString()));
        }

        @Override
        public void onClick(View view) {

        }
    }
}
