package com.sr.datagen.utilities;

import com.sr.datagen.models.Card;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;


import org.junit.jupiter.api.Assertions;
@Slf4j(topic = "CardGeneratorTests")
public class CardGeneratorTests {

    @Test
    public void testCardGeneratorForGenuineCard() {
        Card card = CardGenerator.generateRandomCard(1, 1);
        if(card.getCardType().equals("VISA")) {
            Assertions.assertTrue(true);
            log.info("Visa test passed.");
        } else if(card.getCardType().equals("MASTERCARD")) {
            Assertions.assertTrue(true);
            log.info("Mastercard test passed.");
        } else if(card.getCardType().equals("AMERICAN_EXPRESS")) {
            Assertions.assertTrue(true);
            log.info("American Express test passed.");
        } else if(card.getCardType().equals("DISCOVER")) {
            Assertions.assertTrue(true);
            log.info("Discover test passed.");
        } else {
            log.info("Card type: " + card.getCardType());
            log.info("Card number: " + card.getCardNumber());
            Assertions.assertTrue(false);

        }
        log.info("Card generated successfully.");
        log.info("Card type: " + card.getCardType());
        log.info("Card number: " + card.getCardNumber());
        log.info("Card CVV: " + card.getCvv());
        log.info("Card expiry date: " + card.getExpiryDate());
    }

}
