package tdtu.fit.hrz.midterm;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    private Button buttonSelectDate;
    private TextView selectedDate;
    private DatePickerDialog datePickerDialog;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        buttonSelectDate = findViewById(R.id.changeDateButton);
        selectedDate = findViewById(R.id.selectedDate);

        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        datePickerDialog = new DatePickerDialog(this, dateSetListener, year, month, day);

        buttonSelectDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Hiển thị DatePickerDialog khi người dùng nhấn vào nút
                datePickerDialog.show();
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

}