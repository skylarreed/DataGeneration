package com.sr.datagen.processors;

import com.sr.datagen.models.State;
import com.sr.datagen.models.Transaction;
import com.sr.datagen.utilities.StatePopulator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
@Component
@Slf4j(topic = "StateProcessor")
public class StateProcessor implements ItemProcessor<Transaction, State> {
    private final List<String> states = new ArrayList<>();
    private final StatePopulator statePopulator = new StatePopulator();
    @Override
    public State process(Transaction transaction) throws Exception {
        String stateAbbr = transaction.getMerchantState();
        synchronized (this) {
            if (!states.contains(stateAbbr) && statePopulator.isValidState(stateAbbr)) {
                states.add(stateAbbr);
                return statePopulator.getState(stateAbbr);
            }
            return null;
        }
    }
    public void clearStates() {
        states.clear();
    }
}
