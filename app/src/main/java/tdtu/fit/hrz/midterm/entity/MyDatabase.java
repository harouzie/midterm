package tdtu.fit.hrz.midterm.entity;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;


/**
 * Database for querying ana statistical screen
 * implement Singleton, DAO pattern
 */
public class MyDatabase implements InterfaceTransactionDao{
    private static MyDatabase instance;
    private static ArrayList<Transaction> transactionList;
    private MyDatabase() {
        transactionList = new ArrayList<>();
    }

    public static MyDatabase getInstance() {
        if(instance == null) {
            instance = new MyDatabase();
        }
        return instance;
    }

    @Override
    public Transaction getSingleTransaction() {
        return InterfaceTransactionDao.super.getSingleTransaction();
    }

    @Override
    public Transaction getSingleTransaction(int transactionId) {
        return InterfaceTransactionDao.super.getSingleTransaction(transactionId);
    }

    @Override
    public boolean addSingleTransaction(@NonNull Transaction newTransaction) {
        return InterfaceTransactionDao.super.addSingleTransaction(newTransaction);
    }

    @Override
    public boolean addMultipleTransactions(@NonNull List<Transaction> newTransactions) {
        return InterfaceTransactionDao.super.addMultipleTransactions(newTransactions);
    }

    @Override
    public boolean removeSingleTransaction(int transactionId) {
        return InterfaceTransactionDao.super.removeSingleTransaction(transactionId);
    }

    @Override
    public boolean removeMultipleTransactions(@NonNull List<Integer> transactionIds) {
        return InterfaceTransactionDao.super.removeMultipleTransactions(transactionIds);
    }
}
