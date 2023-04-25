package com.sr.datagen.writers;

import com.sr.datagen.models.*;
import jakarta.persistence.EntityManagerFactory;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.database.builder.JpaItemWriterBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class DBWriter {

    private final EntityManagerFactory entityManagerFactory;

    public DBWriter(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    @Bean
    public JpaItemWriter<User> userDatabaseWriter() {
        return new JpaItemWriterBuilder<User>()
                .entityManagerFactory(entityManagerFactory)
                .build();
    }

    @Bean
    public JpaItemWriter<Card> cardDatabaseWriter() {
        return new JpaItemWriterBuilder<Card>()
                .entityManagerFactory(entityManagerFactory)
                .build();
    }

    @Bean JpaItemWriter<Merchant> merchantDatabaseWriter() {
        return new JpaItemWriterBuilder<Merchant>()
                .entityManagerFactory(entityManagerFactory)
                .build();
    }

    @Bean
    public JpaItemWriter<TransactionDB> transactionDatabaseWriter() {
        return new JpaItemWriterBuilder<TransactionDB>()
                .entityManagerFactory(entityManagerFactory)
                .build();
    }
}
