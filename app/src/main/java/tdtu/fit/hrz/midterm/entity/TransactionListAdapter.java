package tdtu.fit.hrz.midterm.entity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;

public class TransactionListAdapter extends BaseAdapter {

    private final Context context;
    private ArrayList<Transaction> items;
    private final int resourceId;

    public TransactionListAdapter(Context context, ArrayList<Transaction> items, int resourceId) {
        this.context = context;
        this.items = items;
        this.resourceId = resourceId;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int i) {
        return items.get(i);
    }

    @Override
    public long getItemId(int i) {
        return items.get(i).getTransactionId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            convertView = LayoutInflater
                    .from(context)
                    .inflate(this.resourceId, parent, false);
        }
        TransactionViewHolder holder = new TransactionViewHolder(this.context, convertView);
        holder.updateListItem(items.get(position));
        return convertView;
    }
}
