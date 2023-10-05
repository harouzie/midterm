package tdtu.fit.hrz.midterm.entity;
import androidx.annotation.NonNull;

import java.util.List;

/**
 * Define the behaviors the transaction DAO, including getting,
 * adding, deleting transaction, so on...
 */
public interface InterfaceTransactionDao {

//  GETTERS
    default Transaction getSingleTransaction() {
        return getSingleTransaction(0);
    }

//  GETTERS
    default Transaction getSingleTransaction(int transactionId) {
        return null;
    }

    //  ADD-ERS
    default boolean addSingleTransaction(@NonNull Transaction newTransaction) {
        return false; // return false if add failed
    }

    default boolean addMultipleTransactions(@NonNull List<Transaction> newTransactions) {
        return false; // return false if add failed
    }

    //  REMOVERS
    default boolean removeSingleTransaction(int transactionId) {
        return false; // return false if remove failed
    }

    default boolean removeMultipleTransactions(@NonNull List<Integer> transactionIds) {
        return false; // return false if remove failed
    }
}
