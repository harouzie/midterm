package tdtu.fit.hrz.midterm.entity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import tdtu.fit.hrz.midterm.R;
import tdtu.fit.hrz.midterm.TransactionDetailActivity;

public class TransactionViewHolder extends RecyclerView.ViewHolder
        implements View.OnLongClickListener, View.OnClickListener {
    TransactionRCVAdapter mAdapter;
    Transaction transaction;
    ImageView transaction_icon;
    TextView transaction_cate, transaction_amount,
            transaction_date, transaction_currency, transaction_note;
    Context context;
    int resourceId;

    // FOR RCV
    public TransactionViewHolder(
            @NonNull View itemView, TransactionRCVAdapter transactionRCVAdapter,
            Context context, int resourceId) {
        super(itemView);
        mAdapter = transactionRCVAdapter;
        this.resourceId = resourceId;
        this.context = context;
        transaction_icon = itemView.findViewById(R.id.transaction_icon);
        transaction_cate = itemView.findViewById(R.id.transaction_category);
        transaction_amount = itemView.findViewById(R.id.transaction_amount);
        transaction_date = itemView.findViewById(R.id.transaction_date);
        transaction_currency = itemView.findViewById(R.id.transaction_currency);
        transaction_note = itemView.findViewById(R.id.transaction_note);

        itemView.setOnLongClickListener(this);
        itemView.setOnClickListener(this);
    }

    public void updateRCVHolder(Transaction transaction) {
        this.transaction = transaction;
        transaction_icon.setImageResource(transaction.getCategory().getResourceId());
        transaction_cate.setText(String.format("%s", transaction.getCategory()));
        transaction_amount.setText(
                String.format("%s", transaction.getSpentAmountString()));
        transaction_currency.setText(
                String.format("%s", transaction.getCurrency().getCurrencyCode()));
        String note = transaction.getNote().toString();
        if (note.length() > 15){
            note = note.substring(0, 15) + "...";
        }
        transaction_note.setText(note);

        if (this.resourceId == R.layout.transaction_itemlist){

        } else {
            transaction_date.setText(String.format("%s", transaction.getSpentDateString()));
        }

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

    /**
     * @param view transaction view holder
     */
    @Override
    public void onClick(View view) {
        Intent displayIntent = new Intent(context, TransactionDetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt("transaction_id", transaction.getTransactionId());
        displayIntent.setAction(TransactionRequest.DISPLAY.getAction());
        displayIntent.putExtras(bundle);

        context.startActivity(displayIntent);
    }
}
