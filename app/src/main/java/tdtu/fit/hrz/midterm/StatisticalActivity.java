package tdtu.fit.hrz.midterm;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.ColorSpace;
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

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistical);

        mRecyclerView = findViewById(R.id.expListOnCategory);
        filterBtn = findViewById(R.id.filterBtn);
        info = findViewById(R.id.textView);


        //exp list
        ArrayList<Transaction> transactions =
                transactionDAO.getTransactionList();

        mTransactionAdapter = new TransactionRCVAdapter(
                this, transactions, R.layout.transaction_cardview_item_rcv);
        mRecyclerView.setAdapter(mTransactionAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        //category list
        List<TransactionCategory> categories = new ArrayList<>(Arrays.asList(TransactionCategory.values()));

        CategoryAdapter categoryAdapter = new CategoryAdapter(categories);
        RecyclerView listOfCategory = findViewById(R.id.listOfCategory);
        listOfCategory.setLayoutManager(new LinearLayoutManager(this));

        categoryAdapter.setOnItemClickListener(this);
        listOfCategory.setAdapter(categoryAdapter);

        //filter button
        filterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedCategory != null) {
                    ArrayList<Transaction> transactions =
                            transactionDAO.filterByCategory(selectedCategory);

                    mTransactionAdapter = new TransactionRCVAdapter(
                            StatisticalActivity.this, transactions, R.layout.transaction_cardview_item_rcv);
                    mRecyclerView.setAdapter(mTransactionAdapter);
                    mRecyclerView.setLayoutManager(new LinearLayoutManager(StatisticalActivity.this));

                    DecimalFormat df = new DecimalFormat("#.##");
                    String percentage = df.format(
                        (float) transactionDAO.filterByCategory(selectedCategory).size()/
                                transactionDAO.getTransactionList().size()*100);
                    info.setText("Percentage of this kind: " + percentage + "%");
                }
            }
        });

        //PieChart

        pieChart = findViewById(R.id.pieChart);

        // Create a HashMap to map each category to a color
        HashMap<TransactionCategory, Integer> categoryColors = new HashMap<>();

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
                                    R.color.pie10};

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


        // Create a list of entries
        ArrayList<PieEntry> entries = new ArrayList<>();
        for (TransactionCategory category : categories) {
            float percentage = (float)transactionDAO.filterByCategory(category).size() / transactionDAO.getTransactionList().size() * 100;
            entries.add(new PieEntry(percentage));
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
}