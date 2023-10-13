package tdtu.fit.hrz.midterm.entity;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.Currency;
import java.util.Date;

public class DailyReport {

    private ArrayList<Transaction> transactions;
    private Date date;
    private int totalSpent;
    private Currency currency;
    private int numExpenses;

    public DailyReport(){
        this.transactions = new ArrayList<>();
        this.currency = Transaction.currency;
        this.totalSpent = -1;
        this.numExpenses = 0;
    }
    public DailyReport(@NonNull Date date) {
        this();
        this.date = date;
    }
    public void addTransaction(Transaction t){
        this.transactions.add(t);
    }

    private int calculateTotalSpent(){
        int sum = 0;
        for (Transaction transaction: transactions){
            sum += transaction.getSpentAmount();
        }
        return sum;
    }
    public ArrayList<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(ArrayList<Transaction> transactions) {
        this.transactions = transactions;
    }
    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getTotalSpent() {
        totalSpent = calculateTotalSpent();
        return totalSpent;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public int getNumExpenses() {
        return numExpenses;
    }

    public void setNumExpenses(int numExpenses) {
        this.numExpenses = numExpenses;
    }
}
