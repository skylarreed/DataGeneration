package com.sr.datagen.jobs;

import com.sr.datagen.listeners.MainChunkListener;
import com.sr.datagen.listeners.MainJobListener;
import com.sr.datagen.models.Card;
import com.sr.datagen.models.Transaction;
import com.sr.datagen.processors.CardGenerationProcessor;
import com.sr.datagen.readers.ListTransactionReader;
import org.springframework.batch.core.*;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.support.SynchronizedItemStreamReader;
import org.springframework.batch.item.support.SynchronizedItemStreamWriter;
import org.springframework.batch.item.xml.StaxEventItemWriter;
import org.springframework.batch.item.xml.builder.StaxEventItemWriterBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.oxm.xstream.XStreamMarshaller;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.transaction.PlatformTransactionManager;

import java.util.Map;

@Configuration
public class CardGenerationJob {

    private final PlatformTransactionManager transactionManager;

    private final JobRepository jobRepository;

    private final SynchronizedItemStreamReader<Transaction> reader;

    private final CardGenerationProcessor cardGenerationProcessor;

    private final SynchronizedItemStreamWriter<Card> writer;

    private final ListTransactionReader listTransactionReader;

    private final JpaItemWriter<Card> cardDatabaseWriter;

    private final ThreadPoolTaskExecutor taskExecutor;

    @Autowired
    public CardGenerationJob(PlatformTransactionManager transactionManager, JobRepository jobRepository,
                             SynchronizedItemStreamReader<Transaction> reader,
                             CardGenerationProcessor cardGenerationProcessor, SynchronizedItemStreamWriter<Card> writer,
                             ListTransactionReader listTransactionReader, JpaItemWriter<Card> cardDatabaseWriter,
                             @Qualifier("threadPoolTaskExecutor") ThreadPoolTaskExecutor taskExecutor) {
        this.transactionManager = transactionManager;
        this.jobRepository = jobRepository;
        this.reader = reader;
        this.cardGenerationProcessor = cardGenerationProcessor;
        this.writer = writer;
        this.listTransactionReader = listTransactionReader;
        this.cardDatabaseWriter = cardDatabaseWriter;
        this.taskExecutor = taskExecutor;
    }





    @Bean(name = "cardGenStep")
    public Step cardGenerationStep() {
        return new StepBuilder("cardGenerationStep", jobRepository)

                .<Transaction, Card>chunk(50000, transactionManager)
                .reader(reader)
                .processor(cardGenerationProcessor)
                .writer(cardDatabaseWriter)
                .listener(new MainChunkListener())
//                .listener(new StepExecutionListener() {
//                    @Override
//                    public void beforeStep(StepExecution stepExecution) {
//                        System.out.println("Starting card generation step");
//                        cardGenerationProcessor.clearMap();
//                    }
//
//                    @Override
//                    public ExitStatus afterStep(StepExecution stepExecution) {
//                        System.out.println("Finished card generation step");
//                        return null;
//                    }
//                })
                .taskExecutor(taskExecutor)
                .build();
    }

    @Bean(destroyMethod = "", name = "cardWriter")
    @StepScope
    public SynchronizedItemStreamWriter<Card> cardWriter(@Value("#{jobParameters[fileOutputPath]}") String fileOutputPath){

        XStreamMarshaller marshaller = new XStreamMarshaller();
        Map<String, Class> aliases = Map.of("Card", Card.class);
        marshaller.setAliases(aliases);


        StaxEventItemWriter<Card> writer = new StaxEventItemWriterBuilder<Card>()
                .name("cardWriter")
                .resource(new FileSystemResource(fileOutputPath))
                .marshaller(marshaller)
                .rootTagName("Cards")
                .build();

        SynchronizedItemStreamWriter<Card> synchronizedWriter = new SynchronizedItemStreamWriter<>();
        synchronizedWriter.setDelegate(writer);

        return synchronizedWriter;
    }

    @Bean(name = "cardJob")
    @Primary
    public Job cardGeneration() {
        return new JobBuilder("cardJob", jobRepository)
                .listener(new MainJobListener())
                .start(cardGenerationStep())
                .build();
    }


    @Bean
    public AsyncTaskExecutor cardTaskExecutor() {
        SimpleAsyncTaskExecutor taskExecutor = new SimpleAsyncTaskExecutor();
        taskExecutor.setConcurrencyLimit(10);
        taskExecutor.setThreadNamePrefix("CardTaskExecutor-");
        return taskExecutor;
    }
}
