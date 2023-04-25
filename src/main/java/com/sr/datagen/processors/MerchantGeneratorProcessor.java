package com.sr.datagen.processors;

import com.sr.datagen.models.Merchant;
import com.sr.datagen.models.Transaction;
import com.sr.datagen.utilities.MerchantNameGenerator;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
public class MerchantGeneratorProcessor implements ItemProcessor<Transaction, Merchant> {

    private final HashMap<Long, String> merchantMap = new HashMap<>();
    @Override
    public Merchant process(Transaction transaction) throws Exception {
        Merchant merchant = new Merchant();
        int mcc = transaction.getMcc();
        synchronized (this) {
            if (!merchantMap.containsKey(transaction.getMerchantId()) && mcc != 0) {
                merchant.setMerchantId(transaction.getMerchantId());
                merchant.setMerchantName(MerchantNameGenerator.getMerchantName(mcc));
                merchant.setMerchantCity(transaction.getMerchantCity());
                merchant.setMerchantState(transaction.getMerchantState());
                merchant.setZip(transaction.getZip());
                merchant.setMcc(mcc);
                merchantMap.put(transaction.getMerchantId(), merchant.getMerchantName());
                return merchant;
            }
        }

        return null;
    }

    public void clearMap() {
        merchantMap.clear();
    }

}

