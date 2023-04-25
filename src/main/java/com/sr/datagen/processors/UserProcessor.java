package com.sr.datagen.processors;


import com.github.javafaker.Faker;
import com.sr.datagen.models.Transaction;
import com.sr.datagen.models.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
@Slf4j(topic = "UserProcessor")
public class UserProcessor implements ItemProcessor<Transaction, User> {

    private final HashMap<Long, String> userMap = new HashMap<>();
    private final Faker faker = new Faker();

    @Override
    public User process(Transaction transaction) throws Exception {


        synchronized (this) {
            if (!userMap.containsKey(transaction.getUserId())) {
                User user = new User();
                user.setUserId(transaction.getUserId());
                String firstName = faker.name().firstName();
                String lastName = faker.name().lastName();
                user.setFirstName(firstName);
                user.setLastName(lastName);
                user.setEmail(firstName + "." + lastName + "@smoothceeplusplus.com");
                userMap.put(transaction.getUserId(), firstName);
                if (user.getEmail().contains("'")) {
                    user.setEmail(user.getEmail().replace("'", ""));
                }
                return user;
            }
            return null;
        }
    }

    public void clearMap() {
        userMap.clear();
    }
}

