package tdtu.fit.hrz.midterm.entity;

import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.Random;

// TODO data set changed after update, add, delete

/**
 * Database for querying ana statistical screen
 * implement Singleton, DAO pattern
 */
public class TransactionDAO implements InterfaceTransactionDao{
    private static TransactionDAO instance;
    private static ArrayList<Transaction> transactionList;
    private static ArrayList<DailyReport> dailyReports;
    private static ArrayList<CategoryReport> categoricalReports;
    private int dataSize = 500;
    private static int balance;
    private static Random random;
    public static int numCategory;
    private Date filteredDate = new Date();
    private Calendar calendar = Calendar.getInstance();
    //================================SINGLETON=======================================
    private TransactionDAO() {
        random = new Random();
        numCategory = TransactionCategory.values().length; // number of cate
        transactionList = addSyntheticTransaction(dataSize);
        // Sort transactions by latest date
        sortTransactions();
    }

    public static TransactionDAO getInstance() {
        if(instance == null) {
            instance = new TransactionDAO();
        }
        return instance;
    }

    //=======================================================================
    // DUMMY DATA INITIALIZE PROCEDURES
    private ArrayList<Transaction> addSyntheticTransaction(int num){
        ArrayList<Transaction> trs = new ArrayList<>();
        int amount;
        for (int n = 0; n < num; n++) {
            amount = random.nextInt(1000);
            trs.add(
                new Transaction(
                    TransactionCategory.values()[random.nextInt(numCategory)],
                    (amount*1000)
            ));
        }
        return trs;
    }

    public static void sortTransactions(){
        transactionList.sort(new Comparator<Transaction>() {
            @Override
            public int compare(Transaction transaction, Transaction t1) {
                long val = transaction.getSpentDate().getTime() - t1.getSpentDate().getTime();
                if (val > 0)
                    return -1;
                else if (val == 0) {
                    return 0;
                } else {
                    return 1;
                }
            }
        });
    }
    //=======================================================================
    //    FILTERER

    public ArrayList<CategoryReport> getCategoricalReports(boolean takingIncome){
        collectCategoricalReports(takingIncome);
        //sort by amount spent, descending
        categoricalReports.sort(new Comparator<CategoryReport>() {
            @Override
            public int compare(CategoryReport r1, CategoryReport r2) {
                return r2.getTotalSpent() - r1.getTotalSpent();
            }
        });

        int total = 0;
        for (CategoryReport report: categoricalReports) {
            total += report.getTotalSpent();
        }
        int rank = 1;
        for (CategoryReport report: categoricalReports) {
            report.setPercentage(report.getTotalSpent()*100/(1.0*total));
            report.setRank(rank);
            rank+=1;
        }
        return categoricalReports;
    }

    public ArrayList<DailyReport> getDailyReportList(){
        assert transactionList.size() > 0;
        sortTransactions();
        dailyReports = new ArrayList<>();
        DailyReport currentReport = null;
        Date currentDate = null;

        for(Transaction t : transactionList) {

            // Check if new date
            if( currentDate == null ||
                !DailyReport.isSameDate(t.getSpentDate(),currentDate)) {
                // Create new report for new date
                currentReport = new DailyReport(t.getSpentDate());
                dailyReports.add(currentReport);
                currentDate = t.getSpentDate();
            }
            // Add transaction to current report
            assert currentReport != null;
            currentReport.addTransaction(t);
        }

        return dailyReports;
    }

    public ArrayList<Transaction> filterByCategory(TransactionCategory category){
        ArrayList<Transaction> transactions = new ArrayList<>();
        for (Transaction t: transactionList){
            if(t.getCategory().equals(category)){
                transactions.add(t);
            }
        }
        return transactions;
    }
    public ArrayList<Transaction> filterByMonth(int month){
        ArrayList<Transaction> transactions = new ArrayList<>();
        for (Transaction t: transactionList){
            filteredDate = t.getSpentDate();
            calendar.setTime(filteredDate);
            if(calendar.get(Calendar.MONTH) == (month)){
                transactions.add(t);
            }
        }
        return transactions;
    }

    public ArrayList<Transaction> filterByDate(int day, int month, int year){
        ArrayList<Transaction> transactions = new ArrayList<>();
        for (Transaction t: transactionList){
            filteredDate = t.getSpentDate();
            calendar.setTime(filteredDate);
            if(calendar.get(Calendar.DAY_OF_MONTH) == day &&
                calendar.get(Calendar.MONTH) == (month) &&
                calendar.get(Calendar.YEAR) == year){
                transactions.add(t);
            }
        }
        return transactions;
    }
    //==AUXILIARY SUPPORT========================================================
    public int getBalance(){
        assert dailyReports != null;
        balance = 0;
        for (DailyReport report: dailyReports){
            balance += report.getTotalSpent();
        }
        return balance;
    }
    public int calculateTotalSpent(ArrayList<Transaction> transactions){
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
    private void collectCategoricalReports(boolean onlyIncome){
        categoricalReports = new ArrayList<>();

        for (TransactionCategory category: TransactionCategory.values()){
            if(onlyIncome){
                if (isIncome(category)){
                    categoricalReports.add(new CategoryReport(
                        category, this.filterByCategory(category)));
                }
            } else {
                if (!isIncome(category))
                    categoricalReports.add(new CategoryReport(
                    category, this.filterByCategory(category)));
            }
        }
    }
    public boolean isIncome(TransactionCategory category){
        return (
                category.toString().equals(TransactionCategory.INCOME_GIFT.toString()) ||
                        category.toString().equals(TransactionCategory.INCOME_SALARY.toString())
        );
    }
    //=======================================================================
    public ArrayList<Transaction> getTransactionList() {
        return transactionList;
    }

    @Override
    public Transaction getSingleTransaction(int transactionId) {
        for (Transaction transaction: transactionList){
            if (transaction.getTransactionId() == transactionId){
                return transaction;
            }
        }
        return new Transaction();
    }
    public int getTransactionIndex(int transactionId){
        for (int i = 0; i < transactionList.size(); i++) {
            if (transactionList.get(i).getTransactionId() == transactionId){
                return i;
            }
        }
        return -1;
    }
    public void updateTransaction(int transactionId, Transaction newT){
        int index = getTransactionIndex(transactionId);
        newT.setTransactionId(transactionId);
        transactionList.set(index, newT);
    }

    @Override
    public boolean addSingleTransaction(@NonNull Transaction newTransaction) {
        transactionList.add(newTransaction);
        return true;
    }

    @Override
    public boolean removeSingleTransaction(int transactionId) {
        transactionList.remove(getTransactionIndex(transactionId));
        return true;
    }

    public ArrayList<Transaction> getTransactionListCopy() {
        ArrayList<Transaction> transactions = new ArrayList<>();
        transactions.addAll(transactionList);
        return transactions;
    }
}
