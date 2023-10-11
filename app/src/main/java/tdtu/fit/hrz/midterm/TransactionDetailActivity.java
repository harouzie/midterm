package tdtu.fit.hrz.midterm;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.HashMap;

import tdtu.fit.hrz.midterm.entity.Transaction;
import tdtu.fit.hrz.midterm.entity.TransactionCategory;

public class TransactionDetailActivity extends AppCompatActivity {
    ImageView icon;
    TextView transaction_cate;
    TextView transaction_amount;
    TextView transaction_date;
    TextView transaction_currency;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction_detail);
    }
}