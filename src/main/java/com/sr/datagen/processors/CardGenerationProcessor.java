package com.sr.datagen.processors;

import com.sr.datagen.models.Card;
import com.sr.datagen.models.Transaction;
import com.sr.datagen.utilities.CardGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Slf4j(topic = "CardGenerationProcessor")
@Component
public class CardGenerationProcessor implements ItemProcessor<Transaction, Card> {
    private final HashMap<Long, String> cardMap = new HashMap<>();


    public void clearMap() {
        cardMap.clear();
    }

    @Override
    public Card process(Transaction transaction) throws Exception {
        long userId = transaction.getUserId();
        long cardId = transaction.getCardId();

        synchronized (this) {
            if(cardMap.containsKey(userId)) {
                String cardIds = cardMap.get(userId);
                String[] cardIdArray = cardIds.split(",");
                for (String cardIdString : cardIdArray) {
                    if (cardId == Long.parseLong(cardIdString)) {
                        return null;
                    }
                }

                cardIds = cardIds + "," + cardId;
                cardMap.put(userId, cardIds);


            } else {
                cardMap.put(userId, cardId + "");
            }
        }
        return CardGenerator.generateRandomCard(userId, cardId);
    }
}
