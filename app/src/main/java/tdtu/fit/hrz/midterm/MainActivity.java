package tdtu.fit.hrz.midterm;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import tdtu.fit.hrz.midterm.entity.Transaction;

public class MainActivity extends AppCompatActivity {

    private Button buttonSelectDate;
    private TextView selectedDate, currentTime;
    private DatePickerDialog datePickerDialog;
    private RecyclerView mRecyclerView;
    private TransactionListAdapter mTransactionAdapter;
    private FloatingActionButton fab;
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
        fab = findViewById(R.id.fab);

        //====INITIALIZE ===================================================
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        datePickerDialog = new DatePickerDialog(this, dateSetListener, year, month, day);

        //====TESTING, playground is here bois=========================================
        ArrayList<Transaction> transactions = addSyntheticTransaction(10);
        mTransactionAdapter = new TransactionListAdapter(this, transactions);
        mRecyclerView.setAdapter(mTransactionAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        updateClock(); //Call update clock method



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
                Snackbar.make(view, "Let's add some transaction", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
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
    private ArrayList<Transaction> addSyntheticTransaction(int num){
        ArrayList<Transaction> trs = new ArrayList<>();
        for (int n = 0; n < num; n++) {
            trs.add(new Transaction());
        }
        return trs;
    }

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

}