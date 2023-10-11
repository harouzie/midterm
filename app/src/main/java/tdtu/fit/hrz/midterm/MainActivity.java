package tdtu.fit.hrz.midterm;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.Calendar;

import tdtu.fit.hrz.midterm.entity.Transaction;
import tdtu.fit.hrz.midterm.entity.TransactionDAO;
import tdtu.fit.hrz.midterm.entity.TransactionRequest;

public class MainActivity extends AppCompatActivity {

    private Button buttonSelectDate;
    private TextView selectedDate;
    private DatePickerDialog datePickerDialog;
    private RecyclerView mRecyclerView;
    private TransactionListAdapter mTransactionAdapter;
    private FloatingActionButton fab;
    TransactionDAO transactionDAO = TransactionDAO.getInstance();
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //====FIND VIEW====================================================
        buttonSelectDate = findViewById(R.id.changeDateButton);
        selectedDate = findViewById(R.id.selectedDate);
        mRecyclerView = findViewById(R.id.expListOnDate);
        fab = findViewById(R.id.fab);

        //====INITIALIZE ===================================================
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        datePickerDialog = new DatePickerDialog(this, dateSetListener, year, month, day);

        //====TESTING, playground is here bois=========================================

        ArrayList<Transaction> transactions = transactionDAO.getTransactionList();
        mTransactionAdapter = new TransactionListAdapter(this, transactions, R.layout.transaction_cardview);
        mRecyclerView.setAdapter(mTransactionAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));


        //====CLICK LISTENER SETTING========================================
        buttonSelectDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Hiển thị DatePickerDialog khi người dùng nhấn vào nút
                datePickerDialog.show();
            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent addTransactionIntent = new Intent(MainActivity.this, TransactionDetailActivity.class);
                addTransactionIntent.setAction(TransactionRequest.ADD.getAction());

                startActivity(addTransactionIntent);
            }
        });
    }

    private DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            // Xử lý khi người dùng đã chọn ngày
            String date = dayOfMonth + "/" + (monthOfYear + 1) + "/" + year;
            selectedDate.setText(date);
        }
    };

//  TESTING SECTION=================================================
//  i write some function serving testing sake over here /hrz

}