package com.sr.datagen.utilities;

import com.github.javafaker.CreditCardType;
import com.github.javafaker.Faker;
import com.sr.datagen.models.Card;

import java.util.Random;

public class CardGenerator {
    private static final Random random = new Random();
    private static final Faker faker = new Faker();
    public static Card generateRandomCard(long userId, long cardId) {
        Card card = new Card();

        int randInt = random.nextInt(4);
        switch (randInt){
            case 0:
                card.setCardNumber(faker.finance().creditCard(CreditCardType.VISA));
                card.setCardType("VISA");
                break;
            case 1:
                card.setCardNumber(faker.finance().creditCard(CreditCardType.MASTERCARD));
                card.setCardType("MASTERCARD");
                break;
            case 2:
                card.setCardNumber(faker.finance().creditCard(CreditCardType.AMERICAN_EXPRESS));
                card.setCardType("AMERICAN_EXPRESS");
                break;
            case 3:
                card.setCardNumber(faker.finance().creditCard(CreditCardType.DISCOVER));
                card.setCardType("DISCOVER");
                break;
        }
        card.setCvv(generateCVV(card.getCardType()));
        card.setExpiryDate(generateExpiryDate());
        card.setUserId(userId);
        card.setCardId(cardId);
        return card;
    }

    public static String generateExpiryDate() {
        int month = random.nextInt(12) + 1;
        int year = random.nextInt(10) + 2023;
        if(month < 10) {
            return "0" + month + "/" + year;
        }
        else {
            return month + "/" + year;
        }
    }

    public static String generateCVV(String cardType) {
        if(cardType.equals("VISA") || cardType.equals("MASTERCARD") || cardType.equals("DISCOVER")) {
            return String.valueOf((int)(Math.random() * 900) + 100);
        }
        else {
            return String.valueOf((int)(Math.random() * 9000) + 1000);
        }
    }

}



