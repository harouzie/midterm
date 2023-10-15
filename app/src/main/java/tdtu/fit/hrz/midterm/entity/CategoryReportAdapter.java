package tdtu.fit.hrz.midterm.entity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import tdtu.fit.hrz.midterm.R;

public class CategoryReportAdapter extends BaseAdapter {

    private final Context context;
    private final ArrayList<CategoryReport> items;
    private final int resourceId;

    ImageView category_icon;
    TextView  category_name,category_amount,
            category_currency,category_percentage,category_ranking;
    public CategoryReportAdapter(Context context, ArrayList<CategoryReport> items, int resourceId) {
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
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            convertView = LayoutInflater
                    .from(context)
                    .inflate(this.resourceId, parent, false);
        }
        CategoryReport report = items.get(position);
        category_icon = convertView.findViewById(R.id.category_icon);
        category_name = convertView.findViewById(R.id.category_name);
        category_amount = convertView.findViewById(R.id.category_amount);
        category_currency = convertView.findViewById(R.id.category_currency);
        category_percentage = convertView.findViewById(R.id.category_percentage);
        category_ranking = convertView.findViewById(R.id.category_ranking);

        category_icon.setImageResource(report.getCategory().getResourceId());
        category_name.setText(String.format("%s", report.getCategory()));
        category_amount.setText(
                MyStringFormatter.numberFormat.format(report.getTotalSpent()));
        category_currency.setText(
                String.format("%s", report.getCurrency().getCurrencyCode()));

        if (TransactionCategory.INCOME_GIFT.equals(report.getCategory())
                || TransactionCategory.INCOME_SALARY.equals(report.getCategory())){
            category_amount.setTextColor(context.getResources().getColor(R.color.green));
        } else category_amount.setTextColor(context.getResources().getColor(R.color.dark_red));


        return convertView;
    }
}
