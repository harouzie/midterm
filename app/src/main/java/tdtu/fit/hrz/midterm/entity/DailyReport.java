package tdtu.fit.hrz.midterm.entity;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Currency;
import java.util.Date;
import java.util.Locale;

public class DailyReport {

    private ArrayList<Transaction> transactions;
    private Date date;
    private int totalSpent;
    private Currency currency;
    private int numExpenses;
    private static final Calendar c1 = Calendar.getInstance();
    private static final Calendar c2 = Calendar.getInstance();

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
            if (isIncome(transaction.getCategory())){
                sum += transaction.getSpentAmount();
            } else {
                sum -= transaction.getSpentAmount();
            }
        }
        return sum;
    }
    private boolean isIncome(TransactionCategory category){
        return (
            category.toString().equals(TransactionCategory.INCOME_GIFT.toString()) ||
            category.toString().equals(TransactionCategory.INCOME_SALARY.toString())
        );
    }

    /**
     * @param date1 first date to compare
     * @param date2 2nd date object
     * @return is date1 and date2 have the exact match trio dd/mm/yyyy
     */
     public static boolean isSameDate(@NonNull Date date1,@NonNull Date date2){
        int d1, m1, y1, d2, m2, y2;

        c1.setTime(date1);
        c2.setTime(date2);

        d1 = c1.get(Calendar.DAY_OF_MONTH);
        m1 = c1.get(Calendar.MONTH);
        y1 = c1.get(Calendar.YEAR);

        d2 = c2.get(Calendar.DAY_OF_MONTH);
        m2 = c2.get(Calendar.MONTH);
        y2 = c2.get(Calendar.YEAR);

        return d1 == d2 && m1 == m2 && y1 == y2;
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

    public int getNumExpenses(){
        numExpenses = this.transactions.size();
        return numExpenses;
    }
    public String getNumExpensesString() {
        if (getNumExpenses() == 1){
            return "1 expense";
        }
        return String.format(Locale.US,"%d expenses", this.numExpenses);
    }
}
