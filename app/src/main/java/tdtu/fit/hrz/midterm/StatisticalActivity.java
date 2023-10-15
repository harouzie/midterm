package tdtu.fit.hrz.midterm;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import tdtu.fit.hrz.midterm.entity.CategoryAdapter;
import tdtu.fit.hrz.midterm.entity.Transaction;
import tdtu.fit.hrz.midterm.entity.TransactionCategory;
import tdtu.fit.hrz.midterm.entity.TransactionDAO;
import tdtu.fit.hrz.midterm.entity.TransactionRCVAdapter;

public class StatisticalActivity extends AppCompatActivityModified {

    private Button filterBtn;
    private RecyclerView mRecyclerView;
    private TransactionRCVAdapter mTransactionAdapter;
    TransactionDAO transactionDAO = TransactionDAO.getInstance();

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistical);

        mRecyclerView = findViewById(R.id.expListOnCategory);
        filterBtn = findViewById(R.id.filterBtn);


        //exp list
        ArrayList<Transaction> transactions =
                transactionDAO.filterByCategory(TransactionCategory.INCOME_SALARY);

        mTransactionAdapter = new TransactionRCVAdapter(
                this, transactions, R.layout.transaction_cardview_item_rcv);
        mRecyclerView.setAdapter(mTransactionAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        //category list
        List<TransactionCategory> categories = new ArrayList<>(Arrays.asList(TransactionCategory.values()));

        CategoryAdapter categoryAdapter = new CategoryAdapter(categories);
        RecyclerView listOfCategory = findViewById(R.id.listOfCategory);
        listOfCategory.setLayoutManager(new LinearLayoutManager(this));
        listOfCategory.setAdapter(categoryAdapter);
    }
}