package com.sr.datagen.processors;

import com.sr.datagen.models.Transaction;
import com.sr.datagen.models.TransactionDB;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Component
public class TransactionProcessor implements ItemProcessor<Transaction, TransactionDB> {
    @Override
    public TransactionDB process(Transaction transaction) throws Exception {
        TransactionDB transactionDB = new TransactionDB();
        transactionDB.setId(transaction.getId());
        transactionDB.setUserId(transaction.getUserId());
        transactionDB.setMerchantId(transaction.getMerchantId());
        transactionDB.setCardId(transaction.getCardId());
        transactionDB.setYear(transaction.getYear());
        transactionDB.setMonth(transaction.getMonth());
        transactionDB.setDay(transaction.getDay());
        transactionDB.setTime(transaction.getTime());
        transactionDB.setAmount(transaction.getAmount());
        transactionDB.setType(transaction.getType());
        transactionDB.setErrors(transaction.getErrors());
        transactionDB.setFraud(transaction.isFraud());
        return transactionDB;
    }
}
