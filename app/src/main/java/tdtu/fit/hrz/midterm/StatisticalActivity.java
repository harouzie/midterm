package tdtu.fit.hrz.midterm;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import tdtu.fit.hrz.midterm.entity.CategoryAdapter;
import tdtu.fit.hrz.midterm.entity.Transaction;
import tdtu.fit.hrz.midterm.entity.TransactionCategory;
import tdtu.fit.hrz.midterm.entity.TransactionDAO;
import tdtu.fit.hrz.midterm.entity.TransactionRCVAdapter;

public class StatisticalActivity extends AppCompatActivityModified implements CategoryAdapter.OnItemClickListener {

    private Button filterBtn;
    private RecyclerView mRecyclerView;
    private TransactionRCVAdapter mTransactionAdapter;
    private TransactionCategory selectedCategory;
    private TextView info;
    private PieChart pieChart;
    TransactionDAO transactionDAO = TransactionDAO.getInstance();
    private ArrayList<Transaction> expenseList;
    private ArrayList<Transaction> incomeList;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistical);

        mRecyclerView = findViewById(R.id.expListOnCategory);
        filterBtn = findViewById(R.id.filterBtn);
        info = findViewById(R.id.textView);

        //income list
        incomeList = transactionDAO.filterByCategory(TransactionCategory.INCOME_GIFT);
        incomeList.addAll(transactionDAO.filterByCategory(TransactionCategory.INCOME_SALARY));

        //exp list
        expenseList = transactionDAO.getTransactionListCopy();
        Iterator<Transaction> iterator = expenseList.iterator();
        while (iterator.hasNext()) {
            Transaction expense = iterator.next();
            if (expense.getCategory() == TransactionCategory.INCOME_GIFT || expense.getCategory() == TransactionCategory.INCOME_SALARY) {
                iterator.remove();
            }
        }

        //receive intent bundle
        Bundle extras = getIntent().getExtras();
        String str;

        if (extras != null) {
            str = extras.getString("transaction_id");
        } else {
            str = "GENERAL";
        }

        TransactionCategory preSelectedCategory = TransactionCategory.valueOf(str);

        //render the recycler view
        mTransactionAdapter = new TransactionRCVAdapter(
                this, transactionDAO.filterByCategory(preSelectedCategory), R.layout.transaction_cardview_item_rcv);
        mRecyclerView.setAdapter(mTransactionAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        //render TextView
        String defaultPercentage = getPercentageOfCategory(preSelectedCategory);

        info.setText("Percentage of this kind: " + defaultPercentage + "%");

        //======================Bind category to color
        //category list
        List<TransactionCategory> categories = new ArrayList<>(Arrays.asList(TransactionCategory.values()));

        // Create a HashMap to map each category to a color
        LinkedHashMap<TransactionCategory, Integer> categoryColors = new LinkedHashMap<>();

        // add color to hashmap
        int[] colorIds = new int[] { R.color.pie1,
                R.color.pie2,
                R.color.pie3,
                R.color.pie4,
                R.color.pie5,
                R.color.pie6,
                R.color.pie7,
                R.color.pie8,
                R.color.pie9,
                R.color.pie10,
                R.color.pie11,
                R.color.pie12,
                R.color.pie13,
                R.color.pie14,
                R.color.pie15,
                R.color.pie16,
                R.color.pie17,
                R.color.pie18};

        ArrayList<Integer> colors = new ArrayList<Integer>();
        for (int i = 0; i < colorIds.length; i++) {
            colors.add(ContextCompat.getColor(this, colorIds[i]));
        }

        int counter = 0;
        for (TransactionCategory category : categories) {
            int color = colors.get(counter);
            if (counter < colors.size()-1) {
                counter++;
            }
            categoryColors.put(category, color);
        }

        //load category list
        CategoryAdapter categoryAdapter = new CategoryAdapter(categoryColors);
        RecyclerView listOfCategory = findViewById(R.id.listOfCategory);
        listOfCategory.setLayoutManager(new LinearLayoutManager(this));

        categoryAdapter.setOnItemClickListener(this);
        listOfCategory.setAdapter(categoryAdapter);

        //filter button
        filterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedCategory != null) {
                    ArrayList<Transaction> transactionsOfSelectedCategory =
                            transactionDAO.filterByCategory(selectedCategory);

                    mTransactionAdapter = new TransactionRCVAdapter(
                            StatisticalActivity.this, transactionsOfSelectedCategory, R.layout.transaction_cardview_item_rcv);
                    mRecyclerView.setAdapter(mTransactionAdapter);
                    mRecyclerView.setLayoutManager(new LinearLayoutManager(StatisticalActivity.this));

                    String percentage = getPercentageOfCategory(selectedCategory);
                    info.setText("Percentage of this kind: " + percentage + "%");
                }
            }
        });

        //======================================PieChart

        pieChart = findViewById(R.id.pieChart);

        // Create a list of entries
        ArrayList<PieEntry> entries = new ArrayList<>();
        for (TransactionCategory category : categoryColors.keySet()) {
            if (!transactionDAO.isIncome(category)) {
                float percentage = (float)transactionDAO.calculateTotalSpent(transactionDAO.filterByCategory(category)) / transactionDAO.calculateTotalSpent(expenseList) * 100;
                entries.add(new PieEntry(percentage));
            }
        }

        // Create a PieDataSet
        PieDataSet dataSet = new PieDataSet(entries, "Categories");
        dataSet.setColors(colors);
        dataSet.setValueTextSize(12f);

        class CustomPercentFormatter extends PercentFormatter {
            public CustomPercentFormatter(PieChart pieChart) {
                super(pieChart);
            }

            @Override
            public String getFormattedValue(float value) {
                return super.getFormattedValue(value).replace(" ", "");
            }
        }


        dataSet.setValueFormatter(new CustomPercentFormatter(pieChart));
        dataSet.setSliceSpace(1.5f);

        // Create a PieData object
        PieData data = new PieData(dataSet);

        //setting..
        pieChart.getDescription().setEnabled(false);
        pieChart.setUsePercentValues(true);

        // Set the data and the legend
        pieChart.setData(data);
        pieChart.getLegend().setEnabled(false); // Hide the legend

        // Set the hole radius
        pieChart.setHoleRadius(50f);
        pieChart.setTransparentCircleRadius(55f);

        // Enable rotation of the chart by touch
        pieChart.setRotationEnabled(true);
        pieChart.setHighlightPerTapEnabled(true);
    }

    @Override
    public void onItemClick(TransactionCategory category) {
        selectedCategory = category;
    }

    private String getPercentageOfCategory(TransactionCategory category) {
        String percentage = "?";

        ArrayList<Transaction> transactionsOfSelectedCategory =
                transactionDAO.filterByCategory(category);

        DecimalFormat df = new DecimalFormat("#.##");

        if (!transactionDAO.isIncome(category)) {
            percentage = df.format(
                    (float) transactionDAO.calculateTotalSpent(transactionsOfSelectedCategory)/
                            transactionDAO.calculateTotalSpent(expenseList)*100);
        } else {

            percentage = df.format( (float) transactionDAO.calculateTotalSpent(transactionsOfSelectedCategory) /
                    transactionDAO.calculateTotalSpent(incomeList) * 100);
        }

        return percentage;
    }
}