package tdtu.fit.hrz.midterm;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.icu.text.SimpleDateFormat;

import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
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

import tdtu.fit.hrz.midterm.entity.DailyReport;
import tdtu.fit.hrz.midterm.entity.MyStringFormatter;
import tdtu.fit.hrz.midterm.entity.Transaction;
import tdtu.fit.hrz.midterm.entity.TransactionCategory;
import tdtu.fit.hrz.midterm.entity.TransactionDAO;
import tdtu.fit.hrz.midterm.entity.TransactionRCVAdapter;
import tdtu.fit.hrz.midterm.entity.TransactionRequest;

public class MainActivity extends AppCompatActivityModified {

    private Button buttonSelectDate;
    private ImageButton allTimeButton, statButton, userButton;
    private TextView selectedDate, currentTime, date_amount, month_amount;
    private DatePickerDialog datePickerDialog;
    private static final Calendar calendar = Calendar.getInstance();;
    private  Date today;
    private RecyclerView mRecyclerView;
    private TransactionRCVAdapter mTransactionAdapter;
    private FloatingActionButton fab;
    private  SimpleDateFormat def;
    private boolean isFirstRun;

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
        date_amount = findViewById(R.id.date_exp_amount);
        month_amount = findViewById(R.id.month_exp_amount);
        fab = findViewById(R.id.fab);
        def = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());

        // SET LISTENERS FOR BUTTONS AND VIEWS
        setListeners();
        //====INITIALIZE ===================================================
        updateClock(); //Call update clock method

        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        today = calendar.getTime();
        datePickerDialog = new DatePickerDialog(this, dateSetListener, year, month, day);
        SharedPreferences userPreferences = getSharedPreferences("MyCustomPrefs", MODE_PRIVATE);
        String lastUserName = userPreferences.getString("lastUserName", "Default Username");
        SharedPreferences.Editor editor = userPreferences.edit();
        editor.putBoolean("shouldRestartMainActivity", false);
        editor.apply();
        String currentDate = def.format(new Date());
        boolean shouldReloadDate = userPreferences.getBoolean("shouldReloadSelectedDate", false);

        if (!shouldReloadDate) {
            selectedDate.setText(currentDate);
        } else {
            String lastDate = userPreferences.getString("lastSelectedDate", "01/01/1900");
            selectedDate.setText(lastDate);
            editor.putBoolean("shouldReloadSelectedDate", false);
            editor.apply();
        }

        //====TESTING, playground is here bois=========================================
        // month from calender is index 0
        ArrayList<Transaction> transactions = transactionDAO.filterByDate(day, month, year);
        mTransactionAdapter = new TransactionRCVAdapter(
                        this, transactions, R.layout.transaction_cardview_item_rcv);
        mRecyclerView.setAdapter(mTransactionAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // update amount
        updateExpenseStringByDate(today);
    }

    /**
     * update this month and today expense amount
     */
    private void updateExpenseStringByDate(Date date){
        calendar.setTime(date);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        ArrayList<Transaction> dateTransactions = transactionDAO.filterByDate(day, month, year);
        ArrayList<Transaction> monthTransactions = transactionDAO.filterByMonth(month);
        DailyReport report = new DailyReport(date, dateTransactions);
        int dam = report.getTotalSpent();
        int mam = transactionDAO.calculateTotalSpent(monthTransactions);
        if (dam > 0) date_amount.setTextColor(getResources().getColor(R.color.green));
        if (mam > 0) month_amount.setTextColor(getResources().getColor(R.color.green));
        date_amount.setText(String.format("%s VND", MyStringFormatter.numberFormat.format(dam)));
        month_amount.setText(String.format("%s VND", MyStringFormatter.numberFormat.format(mam)));
    }
    private void setListeners(){
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

                Intent watchHistory = new Intent(getApplicationContext(), HistoryActivity.class);
                startActivity(watchHistory);
            }
        });

        // ACTION FOR STATISTICS BUTTON ON TOOLBAR
        statButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), CategoricalStatisticActivity.class);
                startActivity(intent);
            }
        });

        // ACTION FOR USER INFO BUTTON ON TOOLBAR
        userButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), UserInfoActivity.class);
                intent.putExtra("selectedDate", selectedDate.getText().toString());
                startActivity(intent);
            }
        });
    }

    private DatePickerDialog.OnDateSetListener dateSetListener
        = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            // Xử lý khi người dùng đã chọn ngày
            String date = dayOfMonth + "/" + (monthOfYear + 1) + "/" + year;
            selectedDate.setText(date);
            mTransactionAdapter.updateData(
                transactionDAO.filterByDate(dayOfMonth, monthOfYear, year));

            calendar.set(year, monthOfYear, dayOfMonth);
            updateExpenseStringByDate(calendar.getTime());
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

    @Override
    protected void onResume() {
        super.onResume();

        SharedPreferences userPreferences = getSharedPreferences("MyCustomPrefs", MODE_PRIVATE);
        boolean shouldRestart = userPreferences.getBoolean("shouldRestartMainActivity", false);

        if (shouldRestart) {
            // Khởi động lại MainActivity
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish(); // Kết thúc phiên bản cũ của MainActivity
        }

        // Xóa tín hiệu yêu cầu khởi động lại
        userPreferences.edit().remove("shouldRestartMainActivity").apply();
    }


}