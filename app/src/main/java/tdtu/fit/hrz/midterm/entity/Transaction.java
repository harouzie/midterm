package tdtu.fit.hrz.midterm.entity;
import androidx.annotation.NonNull;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Currency;
import java.util.Date;
import java.util.Locale;

public class Transaction {
    private static int staticTransactionId = 0;
    private int transactionId ;
    private Currency currency;
    private int spentAmount = 0;
    private final static SimpleDateFormat dateFormat
            = new SimpleDateFormat("EEEE, dd/MM/yyyy");
    private final static NumberFormat numberFormat
            = NumberFormat.getNumberInstance(new Locale("en","US"));
    private Date spentDate;
    private TransactionCategory category;
    public StringBuilder note;

//   todo: add note, and image like receive or bill
    public Transaction() {
        this.transactionId = staticTransactionId + 1;
        currency = Currency.getInstance("VND");
//        spentDate = new Date();
        spentDate = RandomDateGenerator.getRandomDate();
        category = TransactionCategory.GENERAL_FEE;
        staticTransactionId +=1;
    }

    public Transaction(TransactionCategory category, int spentAmount){
        this();
        this.category = category;
        this.spentAmount =spentAmount;
    }

    //==========================================================================
    //====SETTERS GETTERS           ===========================================
    //==========================================================================

    public int getSpentAmount() {
        return spentAmount;
    }
    public String getSpentAmountString() {
        return numberFormat.format(spentAmount);
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

    public String getCurrencyCode() {
        return currency.getCurrencyCode();
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
