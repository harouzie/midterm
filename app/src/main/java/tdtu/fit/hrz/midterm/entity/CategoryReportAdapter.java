package tdtu.fit.hrz.midterm.entity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import tdtu.fit.hrz.midterm.R;
import tdtu.fit.hrz.midterm.TransactionDetailActivity;
import tdtu.fit.hrz.midterm.entity.CategoryReportHolder;

public class CategoryReportAdapter extends RecyclerView.Adapter<CategoryReportHolder> {

    private final Context context;
    private final ArrayList<CategoryReport> reports;
    private final int resourceId;
    private LayoutInflater mInflater;

    public CategoryReportAdapter(Context context, ArrayList<CategoryReport> items, int resourceId) {
        super();
        this.context = context;
        this.reports = items;
        this.resourceId = resourceId;
        this.mInflater = LayoutInflater.from(context);
    }

    /**
     * @param parent   The ViewGroup into which the new View will be added after it is bound to
     *                 an adapter position.
     * @param viewType The view type of the new View.
     * @return @CategoryReportHolder
     */
    @NonNull
    @Override
    public CategoryReportHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mItemView = mInflater.inflate(this.resourceId,parent, false);
        return new CategoryReportHolder(mItemView,
                this.context, this.resourceId);
    }

    /**
     * @param holder   The ViewHolder which should be updated to represent the contents of the
     *                 item at the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(@NonNull CategoryReportHolder holder, int position) {
        CategoryReport report = reports.get(position);
        holder.update(report);
    }

    /**
     * @return
     */
    @Override
    public int getItemCount() {
        return reports.size();
    }

}
