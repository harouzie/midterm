package tdtu.fit.hrz.midterm.entity;

import android.os.Parcelable;

import java.util.ArrayList;
import java.util.Currency;

public class CategoryReport {

    private TransactionCategory category;
    private ArrayList<Transaction> transactions;
    private int totalSpent;
    private int rank;
    private double percentage;
    private Currency currency;

    public CategoryReport(TransactionCategory category ,ArrayList<Transaction> transactions) {
        this.category = category;
        this.transactions = transactions;
        totalSpent = getTotalSpent();
        currency = Transaction.currency;
    }
    public TransactionCategory getCategory() {
        return category;
    }

    public void setCategory(TransactionCategory category) {
        this.category = category;
    }

    public int getTotalSpent() {
        totalSpent = 0;
        for (Transaction t: transactions){
            totalSpent += t.getSpentAmount();
        }
        return totalSpent;
    }

    public void setTotalSpent(int totalSpent) {
        this.totalSpent = totalSpent;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public double getPercentage() {
        return percentage;
    }

    public void setPercentage(double percentage) {
        this.percentage = percentage;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }
}
