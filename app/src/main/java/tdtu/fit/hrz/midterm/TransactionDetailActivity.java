package tdtu.fit.hrz.midterm;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

import tdtu.fit.hrz.midterm.entity.Transaction;
import tdtu.fit.hrz.midterm.entity.TransactionCategory;
import tdtu.fit.hrz.midterm.entity.TransactionDAO;

public class TransactionDetailActivity extends AppCompatActivityModified {
    ImageView transaction_icon;
    TextView transaction_cate, transaction_amount, transaction_date,
            transaction_currency, edtTime, edtDate;
    EditText edtAmount, edtNote;
    Button btn_save, btn_delete;
    Spinner spinner;
    static TransactionDAO transactionDAO;
    private static final SimpleDateFormat sdfTime = new SimpleDateFormat("HH:mm");
    private static final SimpleDateFormat sdfDate = new SimpleDateFormat("dd/MM/yyyy");
    static boolean selectionChanged;
    static Transaction mTransaction;
    static String action;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction_detail);
        //=============================================================
        spinner = findViewById(R.id.spinnerCate);
        transaction_icon = findViewById(R.id.transaction_icon);
        transaction_cate = findViewById(R.id.transaction_category);
        transaction_amount = findViewById(R.id.transaction_amount);
        transaction_date = findViewById(R.id.transaction_date);
        transaction_currency = findViewById(R.id.transaction_currency);

        edtAmount = findViewById(R.id.edt_amount);
        edtDate = findViewById(R.id.edt_date);
        edtTime = findViewById(R.id.edt_time);
        edtNote = findViewById(R.id.edt_note);

        btn_save = findViewById(R.id.btn_save);
        btn_delete = findViewById(R.id.btn_delete);
        //=============================================================
        transactionDAO = TransactionDAO.getInstance();

        //=============================================================
        spinner.setAdapter(
                new ArrayAdapter<>(this,
                        android.R.layout.simple_spinner_dropdown_item, loadCategory()));
        // default cant save either delete
                onToggleChanges(false);
        // process update/display + add new transaction
        processIntent(getIntent());
        // TODO: noting get bug
        // editing handler
        setListener();

    }

    private void onToggleChanges(boolean state){
        if (state){
            selectionChanged = true;
            btn_save.setClickable(true);
        } else {
            selectionChanged = false;
        }
    }
    /**
     * Called when the activity has detected the user's press of the back
     * key. The {@link #getOnBackPressedDispatcher() OnBackPressedDispatcher} will be given a
     * chance to handle the back button before the default behavior of
     * {@link Activity#onBackPressed()} is invoked.
     *
     * @see #getOnBackPressedDispatcher()
     */
    @Override
    public void onBackPressed() {
        if (selectionChanged) {
            // If the selection has changed, show a confirmation dialog
            new AlertDialog.Builder(this)
                .setMessage("You have unsaved changes. Do you want to exit?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // User confirmed, exit the activity
                        finish();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // User canceled, do nothing
                    }
                })
                .show();
        } else {
            // If no changes were made, simply exit the activity
            super.onBackPressed();
        }
    }

    private void setListener(){
        spinner.getSelectedItem();
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                TransactionCategory category = (TransactionCategory) adapterView.getSelectedItem();
                if (category.toString() != mTransaction.getCategory().toString()){
                    mTransaction.setCategory(category);
                    transaction_cate.setText(category.toString());
                    onToggleChanges(true);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        //======================= TextChangedListener
        edtAmount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String s = editable.toString();
                if (s.isEmpty() || s.length() > 9){
                    edtAmount.setText("0");
                } else {
                    int am = Integer.parseInt(s);
                    mTransaction.setSpentAmount(am);
                    transaction_amount.setText(mTransaction.getSpentAmountString());
                    onToggleChanges(true);
                }
            }
        });
        edtNote.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String s = editable.toString();
                if (s.isEmpty()){
                    edtNote.setText(" ");
                } else {
                    onToggleChanges(true);
                    mTransaction.setNote(new StringBuilder(s));
                }
            }
        });

        //======================== ClickListener
        edtDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Get the current date
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                // Create and show the DatePickerDialog using an anonymous class
                new DatePickerDialog(TransactionDetailActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int day) {
                        calendar.set(year, month, day);
                        Date date = calendar.getTime();

                        edtDate.setText(sdfDate.format(date));
                        transaction_date.setText(sdfDate.format(date));
                        mTransaction.setSpentDate(date);
                        onToggleChanges(true);
                    }
                }, year, month, day).show();
            }
        });
        edtTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                int hour = calendar.get(Calendar.HOUR_OF_DAY);
                int minute = calendar.get(Calendar.MINUTE);
                // Create and show the TimePickerDialog using an anonymous class
                new TimePickerDialog(TransactionDetailActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        calendar.setTime(mTransaction.getSpentDate());
                        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                        calendar.set(Calendar.MINUTE, minute);
                        Date date = calendar.getTime();

                        edtTime.setText(sdfTime.format(date));
                        mTransaction.setSpentDate(date);
                        onToggleChanges(true);
                    }
                }, hour, minute, true).show();
            }
        });
    }

    private void processIntent(Intent intent){
        String action = intent.getAction();
        Bundle bundle = intent.getExtras();
        TransactionDetailActivity.action = action;

        if (action != null){
            switch (action){
                case "display":{
                    if (bundle != null) {
                        int id = bundle.getInt("transaction_id");
                        Transaction transaction = transactionDAO.getSingleTransaction(id);
                        this.updateView(transaction);
                        break;
                    }
                }
                case "add":{
                    Transaction newT = new Transaction();
                    this.updateView(newT);
                    break;
                }
            }
        }
    }

    private ArrayList<TransactionCategory> loadCategory(){
        ArrayList<TransactionCategory> categories = new ArrayList<>();
        Collections.addAll(categories, TransactionCategory.values());
        categories.sort(new Comparator<TransactionCategory>() {
            @Override
            public int compare(TransactionCategory category, TransactionCategory t1) {
                return category.toString().compareTo(t1.toString());
            }
        });
        return categories;
    }

    /**
     * @param transaction
     * update 2 cardviews on this activity
     * each has 4 fields, and 1 spinner
     */
    public void updateView(Transaction transaction) {
        mTransaction = transaction;
        String amount = transaction.getSpentAmountString();
        String date = transaction.getSpentDateString();

        transaction_icon.setImageResource(transaction.getCategory().getResourceId());
        transaction_cate.setText(String.valueOf(transaction.getCategory()));
        transaction_amount.setText(amount);
        transaction_currency.setText(transaction.getCurrency().getCurrencyCode());
        transaction_date.setText(date);

        if (TransactionCategory.INCOME_GIFT.equals(transaction.getCategory())
                || TransactionCategory.INCOME_SALARY.equals(transaction.getCategory())){
            transaction_amount.setTextColor(this.getResources().getColor(R.color.green));
        } else transaction_amount.setTextColor(this.getResources().getColor(R.color.dark_red));

        spinner.setSelection(loadCategory().indexOf(transaction.getCategory()));

        edtAmount.setText(String.valueOf(transaction.getSpentAmount()));
        edtDate.setText(sdfDate.format(transaction.getSpentDate()));
        edtTime.setText(sdfTime.format(transaction.getSpentDate()));
        edtNote.setText(transaction.getNote().toString());
    }

    public void deleteTransaction(View view) {
        if (action.equals("display")) {
            new AlertDialog.Builder(this)
                .setMessage("Do you want to delete this transaction?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        transactionDAO.removeSingleTransaction(mTransaction.getTransactionId());
                        onToggleChanges(false);
                        finish();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).show();
        } else {
            Toast.makeText(this,
                R.string.cant_delete_msg, Toast.LENGTH_SHORT).show();
            transactionDAO.removeSingleTransaction(mTransaction.getTransactionId());
        }
    }

    public void saveTransaction(View view) {
        switch (action){
            case "add":{
                if (selectionChanged){
                    transactionDAO.addSingleTransaction(mTransaction);
                    finish();
                } else {
                    Toast.makeText(this,
                        R.string.not_changed_anything, Toast.LENGTH_SHORT).show();
                }
                break;
            }
            case "display":{
                int id = mTransaction.getTransactionId();
                transactionDAO.updateTransaction(id, mTransaction);
                updateView(mTransaction);
                finish();
                break;
            }
        }
        onToggleChanges(false);
    }
}