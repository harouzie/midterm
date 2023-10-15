package tdtu.fit.hrz.midterm;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

import tdtu.fit.hrz.midterm.entity.CategoryAdapter;
import tdtu.fit.hrz.midterm.entity.CategoryReportAdapter;
import tdtu.fit.hrz.midterm.entity.TransactionDAO;

public class CategoricalStatisticActivity extends AppCompatActivity {

    private ListView category_list;
    private CategoryReportAdapter mAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categorical_statistic);

        TransactionDAO mDao = TransactionDAO.getInstance();
        category_list = findViewById(R.id.category_stat_list);
        mAdapter = new CategoryReportAdapter(this, mDao.getCategoricalReports(),
                R.layout.category_stat_cardview);
        category_list.setAdapter(mAdapter);
    }
}