package tdtu.fit.hrz.midterm.entity;
import androidx.annotation.NonNull;

import java.util.Currency;
import java.util.Date;

public class Transaction {
    private static int staticTransactionId = 0;
    private int transactionId ;
    public static Currency currency;
    private int spentAmount = 0;

    private Date spentDate;
    private TransactionCategory category;
    private StringBuilder note;

//   todo: add note, and image like receive or bill
    public Transaction() {
        this.transactionId = staticTransactionId + 1;
        currency = Currency.getInstance("VND");
//        spentDate = new Date();
        spentDate = new Date();
        category = TransactionCategory.GENERAL;
        staticTransactionId +=1;
        note = new StringBuilder("");
    }

    public Transaction(TransactionCategory category, int spentAmount){
        this();
        this.category = category;
        this.spentAmount =spentAmount;
        spentDate = RandomDateGenerator.getRandomDate();
    }

    //==========================================================================
    //====SETTERS GETTERS           ===========================================
    //==========================================================================

    public int getSpentAmount() {
        return spentAmount;
    }
    public String getSpentAmountString() {
        return MyStringFormatter.numberFormat.format(spentAmount);
    }
    public void setSpentAmount(int amount){
        spentAmount = amount;
    }
    //================================================
    public int getTransactionId() {
        return transactionId;
    }
    public void setTransactionId(int id) {
        this.transactionId = id;
    }
    //================================================
    public Date getSpentDate() {
        return spentDate;
    }
    public String getSpentDateString() {
        return MyStringFormatter.fullDateFormat.format(spentDate);
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
        Transaction.currency = currency;
    }

    public StringBuilder getNote() {
        return note;
    }

    public void setNote(StringBuilder note) {
        this.note = note;
    }
    @NonNull
    public String toString(){
        return String.format(
                "Transaction{ID:%d}[%s|%s|%s|%s]",
                transactionId, getCategory().toString(),
                getSpentAmountString(), getSpentDateString(),getNote().toString()
        );
    }
}
