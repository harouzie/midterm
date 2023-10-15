package tdtu.fit.hrz.midterm;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
                    String percentage = df.format((float) transactionDAO.filterByCategory(selectedCategory).size()/transactionDAO.getTransactionList().size()*100);
                    info.setText("Percentage of this kind: " + percentage + "%");
                }
            }
        });
    }

    @Override
    public void onItemClick(TransactionCategory category) {
        selectedCategory = category;
    }
}