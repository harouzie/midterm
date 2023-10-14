package tdtu.fit.hrz.midterm;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.TextView;

import tdtu.fit.hrz.midterm.entity.DailyReportAdapter;
import tdtu.fit.hrz.midterm.entity.MyStringFormatter;
import tdtu.fit.hrz.midterm.entity.TransactionDAO;

public class HistoryActivity extends AppCompatActivity {

    private RecyclerView rcv;
    TextView tvBalance;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        rcv = findViewById(R.id.rcv_days);
        tvBalance = findViewById(R.id.balance);

        TransactionDAO dao = TransactionDAO.getInstance();
        DailyReportAdapter reportAdapter = new DailyReportAdapter(
        this, dao.getDailyReportList(), R.layout.daily_report_item);
        rcv.setAdapter(reportAdapter);
        rcv.setLayoutManager(new LinearLayoutManager(this));
        int balance = dao.getBalance();
        if (balance > 0){
            tvBalance.setTextColor(getResources().getColor(R.color.green));
        } else tvBalance.setTextColor(getResources().getColor(R.color.dark_red));
        tvBalance.setText(MyStringFormatter.numberFormat.format(balance));
    }
}