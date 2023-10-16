package tdtu.fit.hrz.midterm;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.Switch;

import tdtu.fit.hrz.midterm.entity.CategoryReportAdapter;
import tdtu.fit.hrz.midterm.entity.TransactionDAO;

public class CategoricalStatisticActivity extends AppCompatActivity {

    private RecyclerView category_rcv;
    private CategoryReportAdapter mAdapter;
    private Switch incomeSwitch;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categorical_statistic);

        incomeSwitch = findViewById(R.id.income_switch);
        category_rcv = findViewById(R.id.category_stat_list);

        TransactionDAO mDao = TransactionDAO.getInstance();
        mAdapter = new CategoryReportAdapter(this,
                mDao.getCategoricalReports(incomeSwitch.isChecked()),
                R.layout.category_stat_cardview);
        category_rcv.setAdapter(mAdapter);
        category_rcv.setLayoutManager(new LinearLayoutManager(this));

        incomeSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                mAdapter = new CategoryReportAdapter(CategoricalStatisticActivity.this,
                        mDao.getCategoricalReports(b), R.layout.category_stat_cardview);
                category_rcv.setAdapter(mAdapter);
            }
        });
    }
}