package tdtu.fit.hrz.midterm.entity;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

import tdtu.fit.hrz.midterm.R;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {
    private LinkedHashMap<TransactionCategory, Integer> categories;

    private ArrayList<TransactionCategory> keyList;
    private ArrayList<Integer> valueList;

    private int selectedPosition = RecyclerView.NO_POSITION;

    public CategoryAdapter(LinkedHashMap<TransactionCategory, Integer> categories) {
        this.categories = categories;
        this.keyList = new ArrayList<>(categories.keySet());
        this.valueList = new ArrayList<>(categories.values());
    }

    public interface OnItemClickListener {
        void onItemClick(TransactionCategory category);
    }

    private OnItemClickListener listener;

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public class CategoryViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tvCategoryName;
        View color;

        public CategoryViewHolder(View itemView) {
            super(itemView);
            tvCategoryName = itemView.findViewById(R.id.tvCategoryName);
            color = itemView.findViewById(R.id.color);
            itemView.setOnClickListener(this);
        }

        public void onClick(View v) {
            int position = getAdapterPosition();
            if (position != RecyclerView.NO_POSITION) {
                notifyItemChanged(selectedPosition);
                selectedPosition = position;
                notifyItemChanged(selectedPosition);
            }
        }
    }

    @Override
    public CategoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_layout, parent, false);
        return new CategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CategoryViewHolder holder, int position) {

        TransactionCategory category = keyList.get(position);
        int color = valueList.get(position);

        holder.tvCategoryName.setText(category.name());
        holder.color.setBackgroundColor(color);
        if (position == selectedPosition) {
            holder.itemView.setBackgroundColor(Color.GRAY);
        } else {
            holder.itemView.setBackgroundColor(Color.TRANSPARENT);
        }

        if (listener != null) {
            listener.onItemClick(category);
        }
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

}
