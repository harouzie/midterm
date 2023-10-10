package tdtu.fit.hrz.midterm.entity;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;


/**
 * Database for querying ana statistical screen
 * implement Singleton, DAO pattern
 */
public class TransactionDAO implements InterfaceTransactionDao{
    private static TransactionDAO instance;
    private static ArrayList<Transaction> transactionList;
    private TransactionDAO() {
        transactionList = new ArrayList<>();
    }

    public static TransactionDAO getInstance() {
        if(instance == null) {
            instance = new TransactionDAO();
        }
        return instance;
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
