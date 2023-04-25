package com.sr.datagen.jobs;

import com.sr.datagen.listeners.MainChunkListener;
import com.sr.datagen.models.Transaction;
import com.sr.datagen.models.TransactionDB;
import com.sr.datagen.processors.TransactionProcessor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.support.SynchronizedItemStreamReader;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class TransactionGeneration {
    private final PlatformTransactionManager transactionManager;

    private final JobRepository jobRepository;

    private final SynchronizedItemStreamReader<Transaction> reader;

    private final JpaItemWriter<TransactionDB> transactionDatabaseWriter;

    private final TransactionProcessor transactionProcessor;

    private final ThreadPoolTaskExecutor taskExecutor;


    public TransactionGeneration(PlatformTransactionManager transactionManager, JobRepository jobRepository,
                                 SynchronizedItemStreamReader<Transaction> reader,
                                 JpaItemWriter<TransactionDB> transactionDatabaseWriter,
                                 TransactionProcessor transactionProcessor,
                                 @Qualifier("threadPoolTaskExecutor") ThreadPoolTaskExecutor taskExecutor) {
        this.transactionManager = transactionManager;
        this.jobRepository = jobRepository;
        this.reader = reader;
        this.transactionDatabaseWriter = transactionDatabaseWriter;
        this.transactionProcessor = transactionProcessor;
        this.taskExecutor = taskExecutor;

    }

    @Bean(name = "transactionGenStep")
    public Step transactionGenerationStep() {
        return new StepBuilder("transactionGenerationStep", jobRepository)
                .<Transaction, TransactionDB>chunk(10000, transactionManager)
                .listener(new MainChunkListener())
                .reader(reader)
                .processor(transactionProcessor)
                .writer(transactionDatabaseWriter)
                .taskExecutor(taskExecutor)
                .build();
    }

    @Bean
    @Qualifier("transactionGenerationJob")
    public Job transactionGenerationJob() {
        return new JobBuilder("transactionGenerationJob", jobRepository)
                .start(transactionGenerationStep())
                .build();
    }



}
