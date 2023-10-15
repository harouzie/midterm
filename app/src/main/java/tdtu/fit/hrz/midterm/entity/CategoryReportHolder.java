package tdtu.fit.hrz.midterm.entity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import tdtu.fit.hrz.midterm.LanguageManager;
import tdtu.fit.hrz.midterm.R;
import tdtu.fit.hrz.midterm.StatisticalActivity;
import tdtu.fit.hrz.midterm.TransactionDetailActivity;

public class CategoryReportHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    ImageView category_icon;
    TextView category_name,category_amount,
            category_currency,category_percentage,category_ranking;

    CategoryReport report;
    Context context;
    int resourceId;
    public CategoryReportHolder(@NonNull View itemView, Context context, int resourceId) {
        super(itemView);
        this.context = context;
        this.resourceId = resourceId;
        category_icon = itemView.findViewById(R.id.category_icon);
        category_name = itemView.findViewById(R.id.category_name);
        category_amount = itemView.findViewById(R.id.category_amount);
        category_currency = itemView.findViewById(R.id.category_currency);
        category_percentage = itemView.findViewById(R.id.category_percentage);
        category_ranking = itemView.findViewById(R.id.category_ranking);

        itemView.setOnClickListener(this);
    }

    public void update(CategoryReport report){
        this.report = report;
        category_icon.setImageResource(report.getCategory().getResourceId());
        category_name.setText(String.format("%s", report.getCategory()));
        category_amount.setText(
                MyStringFormatter.numberFormat.format(report.getTotalSpent()));
        category_currency.setText(
                String.format("%s", report.getCurrency().getCurrencyCode()));
        category_percentage.setText(context.getResources().getString(R.string.percentage) +
            String.format(LanguageManager.locale,"%.2f", report.getPercentage()) + " %");
        category_ranking.setText(
            String.format(LanguageManager.locale,"#%02d", report.getRank()));
        if (TransactionCategory.INCOME_GIFT.equals(report.getCategory())
                || TransactionCategory.INCOME_SALARY.equals(report.getCategory())){
            category_amount.setTextColor(context.getResources().getColor(R.color.green));
        } else category_amount.setTextColor(context.getResources().getColor(R.color.dark_red));
    }

    /**
     * @param view CategoryReportHolder
     */
    @Override
    public void onClick(View view) {
        Intent statIntent = new Intent(context, StatisticalActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("transaction_id", report.getCategory().toString());
        statIntent.setAction(TransactionRequest.DISPLAY.getAction());
        statIntent.putExtras(bundle);

        context.startActivity(statIntent);
    }
}
