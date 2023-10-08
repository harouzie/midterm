package tdtu.fit.hrz.midterm.entity;
import androidx.annotation.NonNull;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Currency;
import java.util.Date;

public class Transaction {
    private static int staticTransactionId = 0;
    private final int transactionId;
    private Currency currency;
    private float spentAmount = 0.0f;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd.mm.yyyy");
    private Date spentDate;
    private TransactionCategory category;
//   todo: add note, and image like receive or bill
    public Transaction() {
        this.transactionId = staticTransactionId + 1;
        currency = Currency.getInstance("VND");
        spentDate = new Date();
        category = TransactionCategory.GENERAL_FEE;
        staticTransactionId +=1;
    }


    public float getSpentAmount() {
        return spentAmount;
    }
    //================================================
    public int getTransactionId() {
        return transactionId;
    }
    //================================================
    public Date getSpentDate() {
        return spentDate;
    }
    public String getSpentDateString() {
        return dateFormat.format(spentDate);
    }

    public void setSpentDate(Date spentDate) {
        this.spentDate = spentDate;
    }
    //================================================
    public TransactionCategory getCategory() {
        return category;
    }
    public void setCategory(TransactionCategory category) {
        this.category = category;
    }
    //================================================
    public Currency getCurrency() {
        return currency;
    }
    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    @NonNull
    public String toString(){
        return String.format(
        "Transaction{ID:%d}",transactionId
        );
    }
}
