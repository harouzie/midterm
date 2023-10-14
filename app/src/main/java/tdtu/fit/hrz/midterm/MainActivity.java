package tdtu.fit.hrz.midterm;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;

import android.icu.text.SimpleDateFormat;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import tdtu.fit.hrz.midterm.entity.Transaction;
import tdtu.fit.hrz.midterm.entity.TransactionCategory;
import tdtu.fit.hrz.midterm.entity.TransactionDAO;
import tdtu.fit.hrz.midterm.entity.TransactionRCVAdapter;
import tdtu.fit.hrz.midterm.entity.TransactionRequest;

public class MainActivity extends AppCompatActivityModified {

    private Button buttonSelectDate;
    private ImageButton allTimeButton, statButton, userButton;
    private TextView selectedDate, currentTime;
    private DatePickerDialog datePickerDialog;
    private RecyclerView mRecyclerView;
    private TransactionRCVAdapter mTransactionAdapter;
    private FloatingActionButton fab;

    TransactionDAO transactionDAO = TransactionDAO.getInstance();

    Handler handler = new Handler();

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //====FIND VIEW====================================================
        buttonSelectDate = findViewById(R.id.changeDateButton);
        selectedDate = findViewById(R.id.selectedDate);
        mRecyclerView = findViewById(R.id.expListOnDate);
        currentTime = findViewById(R.id.timeTextView);
        allTimeButton = findViewById(R.id.allTimeButton);
        statButton = findViewById(R.id.statButton);
        userButton = findViewById(R.id.userButton);
        fab = findViewById(R.id.fab);

        //====INITIALIZE ===================================================
        updateClock(); //Call update clock method
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        datePickerDialog = new DatePickerDialog(this, dateSetListener, year, month, day);

        //====TESTING, playground is here bois=========================================

        ArrayList<Transaction> transactions =
//                transactionDAO.filterByDate(9, 10, 2023);
//                transactionDAO.filterByMonth(9);
//                transactionDAO.filterByCategory(TransactionCategory.INCOME_SALARY);
                transactionDAO.getTransactionList(); // return all dataset

        mTransactionAdapter = new TransactionRCVAdapter(
                        this, transactions, R.layout.transaction_cardview_item_rcv);
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

        // ACTION FOR ALL-TIME BUTTON ON TOOLBAR
        allTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Sample Action for All Time History",
                        Toast.LENGTH_SHORT).show();
                Intent watchHistory = new Intent(MainActivity.this, HistoryActivity.class);
                startActivity(watchHistory);
            }
        });

        // ACTION FOR STATISTICS BUTTON ON TOOLBAR
        statButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Sample Action for Statistics",
                        Toast.LENGTH_SHORT).show();
            }
        });

        // ACTION FOR USER INFO BUTTON ON TOOLBAR
        userButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), UserInfoActivity.class);
                startActivity(intent);
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

    private void updateClock() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy HH:mm", Locale.getDefault());
                String currentDateAndTime = sdf.format(new Date());
                currentTime.setText(currentDateAndTime);
                updateClock();
            }
        }, 1000); // 1000 means update every 1 second
    }

//    public void startHistoryActivity(View view) {
//
//    }
}