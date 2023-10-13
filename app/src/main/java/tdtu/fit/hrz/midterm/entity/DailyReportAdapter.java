package tdtu.fit.hrz.midterm.entity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import tdtu.fit.hrz.midterm.R;

public class DailyReportAdapter
    extends RecyclerView.Adapter<DailyReportAdapter.DailyReportViewHolder> {
    private Context context;
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
     * @return
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
     * @return
     */
    @Override
    public int getItemCount() {
        return reports.size();
    }

    public class DailyReportViewHolder extends RecyclerView.ViewHolder {

        private Context context;
        TextView report_date,report_weekday,report_num_exp,report_total_amount,report_currency;
        final SimpleDateFormat dayFormater = new SimpleDateFormat("dd");
        final SimpleDateFormat dateFormater = new SimpleDateFormat("EEEE, MMMM, yyy");

        DailyReport report;

        public DailyReportViewHolder(@NonNull View itemView, Context context) {
            super(itemView);
            this.context = context;
            report_date = itemView.findViewById(R.id.report_date);
            report_weekday = itemView.findViewById(R.id.report_weekday);
            report_num_exp = itemView.findViewById(R.id.report_num_exp);
            report_total_amount = itemView.findViewById(R.id.report_total_amount);
            report_currency = itemView.findViewById(R.id.report_currency);

        }

        public void update(DailyReport report) {
            this.report = report;
            report_date.setText(dayFormater.format(report.getDate()));
            report_weekday.setText(dateFormater.format(report.getDate()));
            report_num_exp.setText(String.valueOf(report.getNumExpenses()));
            report_total_amount.setText(String.valueOf(report.getTotalSpent()));
            report_currency.setText(report.getCurrency().getCurrencyCode());
        }
    }
}
