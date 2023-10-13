package tdtu.fit.hrz.midterm.entity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

/**
 *
 */
public class TransactionRCVAdapter extends RecyclerView.Adapter<TransactionViewHolder> {
    private static ArrayList<Transaction> mTransactionList;
    private LayoutInflater mInflater;
    private Context context;
    private int resourceID;
    public TransactionRCVAdapter(Context context){

    }
    public TransactionRCVAdapter(Context context, ArrayList<Transaction> transactions, int resourceID){
        this.context = context;
        this.mInflater = LayoutInflater.from(context);
        mTransactionList = transactions;
        this.resourceID = resourceID;
    }
    @NonNull
    @Override
    public TransactionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mItemView = mInflater.inflate(this.resourceID,parent, false);
        return new TransactionViewHolder(mItemView, this, this.context);
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

}
