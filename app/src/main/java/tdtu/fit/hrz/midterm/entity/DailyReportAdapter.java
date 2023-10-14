package tdtu.fit.hrz.midterm.entity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import tdtu.fit.hrz.midterm.R;

public class DailyReportAdapter
    extends RecyclerView.Adapter<DailyReportAdapter.DailyReportViewHolder> {
    private final Context context;
    private ArrayList<DailyReport> reports;
    int resourceId;
    private static LayoutInflater mInflater;

    public DailyReportAdapter(Context context, ArrayList<DailyReport> reports, int resourceId) {
        this.context = context;
        this.reports = reports;
        this.resourceId = resourceId;
        mInflater = LayoutInflater.from(context);
    }

    /**
     * @param parent   The ViewGroup into which the new View will be added after it is bound to
     *                 an adapter position.
     * @param viewType The view type of the new View.
     * @return @DailyReportAdapter.DailyReportViewHolder
     */
    @NonNull
    @Override
    public DailyReportAdapter.DailyReportViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mItemView = mInflater.inflate(this.resourceId,parent, false);
        return new DailyReportViewHolder(mItemView, this.context);
    }

    /**
     * @param holder   The ViewHolder which should be updated to represent the contents of the
     *                 item at the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(@NonNull DailyReportAdapter.DailyReportViewHolder holder, int position) {
        DailyReport report = this.reports.get(position);
        holder.update(report);
    }

    /**
     * @return item.size
     */
    @Override
    public int getItemCount() {
        return reports.size();
    }

    public static class DailyReportViewHolder extends RecyclerView.ViewHolder {

        private final Context context;
        TextView report_date,report_weekday,report_num_exp,report_total_amount,report_currency;
        ListView transaction_lv;

        DailyReport report;

        public DailyReportViewHolder(@NonNull View itemView, Context context) {
            super(itemView);
            this.context = context;
            report_date = itemView.findViewById(R.id.report_date);
            report_weekday = itemView.findViewById(R.id.report_weekday);
            report_num_exp = itemView.findViewById(R.id.report_num_exp);
            report_total_amount = itemView.findViewById(R.id.transaction_amount);
            report_currency = itemView.findViewById(R.id.transaction_currency);
            transaction_lv = itemView.findViewById(R.id.report_transactions_list);
        }

        public void update(DailyReport report) {
            this.report = report;
            report_date.setText(MyStringFormatter.dayFormatter.format(report.getDate()));
            report_weekday.setText(MyStringFormatter.weekdayFormatter.format(report.getDate()));
            report_num_exp.setText(report.getNumExpensesString());
            // Add color
            report_total_amount.setText(
                MyStringFormatter.numberFormat.format(report.getTotalSpent())
            );
            if (report.getTotalSpent() < 0) {
                report_total_amount.setTextColor(context.getResources().getColor(R.color.dark_red));
            } else {
                report_total_amount.setTextColor(context.getResources().getColor(R.color.green));
            }

            report_currency.setText(report.getCurrency().getCurrencyCode());
            TransactionListAdapter listAdapter = new
                TransactionListAdapter(this.context,
                    report.getTransactions(), R.layout.transaction_itemlist);
            transaction_lv.setAdapter(listAdapter);
        }
    }
}
