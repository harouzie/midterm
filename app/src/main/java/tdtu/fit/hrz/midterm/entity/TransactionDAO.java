package tdtu.fit.hrz.midterm.entity;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


/**
 * Database for querying ana statistical screen
 * implement Singleton, DAO pattern
 */
public class TransactionDAO implements InterfaceTransactionDao{
    private static TransactionDAO instance;
    private static ArrayList<Transaction> transactionList;
    private int dataSize = 0;
    private final static Random random = new Random(127343342);
    private final static int numCategory = TransactionCategory.values().length; // number of cate
    private TransactionDAO(int dataSize) {
        transactionList = addSyntheticTransaction(dataSize);
        this.dataSize = dataSize;
    }

    public static TransactionDAO getInstance(int dataSize) {
        if(instance == null) {
            instance = new TransactionDAO(dataSize);
        }
        return instance;
    }

    public ArrayList<Transaction> getTransactionList() {
        return transactionList;
    }

    private ArrayList<Transaction> addSyntheticTransaction(int num){
        ArrayList<Transaction> trs = new ArrayList<>();
        int amount;
        for (int n = 0; n < num; n++) {
            amount = random.nextInt(1000000);
            trs.add(
                new Transaction(
                    TransactionCategory.values()[random.nextInt(numCategory)],
                    (amount - amount%1000)
            ));
        }
        return trs;
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
