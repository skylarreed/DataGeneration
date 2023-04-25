package com.sr.datagen.jobs;


import com.sr.datagen.listeners.MainChunkListener;
import com.sr.datagen.listeners.MainJobListener;
import com.sr.datagen.models.Merchant;
import com.sr.datagen.models.Transaction;
import com.sr.datagen.processors.MerchantGeneratorProcessor;
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
public class MerchantGenerationJob {

    private final PlatformTransactionManager transactionManager;

    private final JobRepository jobRepository;

    private final SynchronizedItemStreamReader<Transaction> reader;

    private final MerchantGeneratorProcessor merchantGeneratorProcessor;

    private final SynchronizedItemStreamWriter<Merchant> writer;

    private final ListTransactionReader listTransactionReader;

    private final JpaItemWriter<Merchant> merchantDatabaseWriter;

    private final ThreadPoolTaskExecutor taskExecutor;


    @Autowired
    public MerchantGenerationJob(PlatformTransactionManager transactionManager, JobRepository jobRepository,
                                 SynchronizedItemStreamReader<Transaction> reader,
                                 MerchantGeneratorProcessor merchantGeneratorProcessor,
                                 SynchronizedItemStreamWriter<Merchant> writer,
                                 ListTransactionReader listTransactionReader, JpaItemWriter<Merchant> merchantDatabaseWriter,
                                 @Qualifier("threadPoolTaskExecutor") ThreadPoolTaskExecutor taskExecutor) {
        this.transactionManager = transactionManager;
        this.jobRepository = jobRepository;
        this.reader = reader;
        this.merchantGeneratorProcessor = merchantGeneratorProcessor;
        this.writer = writer;
        this.listTransactionReader = listTransactionReader;
        this.merchantDatabaseWriter = merchantDatabaseWriter;
        this.taskExecutor = taskExecutor;
    }



    @Bean(name = "merchantGenStep")
    public Step merchantGenerationStep() {
        return new StepBuilder("merchantGenerationStep", jobRepository)
                .<Transaction, Merchant>chunk(10000, transactionManager)
                .listener(new MainChunkListener())
                .reader(reader)
                .processor(merchantGeneratorProcessor)
                .writer(merchantDatabaseWriter)
                .listener(new StepExecutionListener() {
                    @Override
                    public void beforeStep(StepExecution stepExecution) {
                        System.out.println("Starting merchant generation step");
                        merchantGeneratorProcessor.clearMap();
                    }

                    @Override
                    public ExitStatus afterStep(StepExecution stepExecution) {
                        System.out.println("Finished merchant generation step");
                        return null;
                    }
                })
                .taskExecutor(taskExecutor)
                .build();
    }


    @Bean(destroyMethod = "")
    @StepScope
    public SynchronizedItemStreamWriter<Merchant> merchantWriter(@Value("#{jobParameters[fileOutputPath]}") String fileOutputPath) {

        XStreamMarshaller marshaller = new XStreamMarshaller();
        Map<String, Class> aliases = Map.of("Merchant", Merchant.class);
        marshaller.setAliases(aliases);


        StaxEventItemWriter<Merchant> writer = new StaxEventItemWriterBuilder<Merchant>()
                .name("merchantWriter")
                .resource(new FileSystemResource(fileOutputPath))
                .marshaller(marshaller)
                .rootTagName("Merchants")
                .build();

        SynchronizedItemStreamWriter<Merchant> synchronizedWriter = new SynchronizedItemStreamWriter<>();
        synchronizedWriter.setDelegate(writer);

        return synchronizedWriter;
    }

    @Bean(name = "merchantJob")
    @Primary
    public Job merchantGeneration() {
        return new JobBuilder("merchantJob", jobRepository)
                .start(merchantGenerationStep())
                .listener(new MainJobListener())
                .build();
    }


    @Bean
    public AsyncTaskExecutor merchantTaskExecutor() {
        return new SimpleAsyncTaskExecutor();
    }
}
