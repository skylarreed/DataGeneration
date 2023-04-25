package com.sr.datagen.jobs;


import com.sr.datagen.listeners.MainChunkListener;
import com.sr.datagen.listeners.MainJobListener;
import com.sr.datagen.models.State;
import com.sr.datagen.models.Transaction;
import com.sr.datagen.processors.StateProcessor;
import com.sr.datagen.readers.ListTransactionReader;
import org.springframework.batch.core.*;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
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
public class StateGenerationJob {

    private final PlatformTransactionManager transactionManager;

    private final JobRepository jobRepository;

    private final SynchronizedItemStreamReader<Transaction> reader;

    private final StateProcessor stateProcessor;

    private final SynchronizedItemStreamWriter<State> writer;

    private final ListTransactionReader listTransactionReader;

    private final ThreadPoolTaskExecutor taskExecutor;
    @Autowired
    public StateGenerationJob(PlatformTransactionManager transactionManager, JobRepository jobRepository,
                              SynchronizedItemStreamReader<Transaction> reader, StateProcessor stateProcessor,
                              SynchronizedItemStreamWriter<State> writer, ListTransactionReader listTransactionReader,
                              @Qualifier("threadPoolTaskExecutor") ThreadPoolTaskExecutor taskExecutor) {
        this.transactionManager = transactionManager;
        this.jobRepository = jobRepository;
        this.reader = reader;
        this.stateProcessor = stateProcessor;
        this.writer = writer;
        this.listTransactionReader = listTransactionReader;
        this.taskExecutor = taskExecutor;
    }



    @Bean(name = "stateGenStep")
    public Step stateGenerationStep() {
        return new StepBuilder("stateGenerationStep", jobRepository)
                .<Transaction, State>chunk(50000, transactionManager)
                .reader(reader)
                .processor(stateProcessor)
                .writer(writer)
                .listener(new StepExecutionListener() {
                    @Override
                    public void beforeStep(StepExecution stepExecution) {
                        System.out.println("Starting state generation step");
                        stateProcessor.clearStates();
                    }

                    @Override
                    public ExitStatus afterStep(StepExecution stepExecution) {
                        System.out.println("Finished state generation step");
                        return null;
                    }
                })
                .listener(new MainChunkListener())
                .taskExecutor(taskExecutor)
                .build();
    }

    @Bean(destroyMethod = "")
    @StepScope
    public SynchronizedItemStreamWriter<State> stateWriter(@Value("#{jobParameters[fileOutputPath]}") String fileOutputPath) {

        XStreamMarshaller marshaller = new XStreamMarshaller();
        Map<String, Class> aliases = Map.of("State", State.class);
        marshaller.setAliases(aliases);


        StaxEventItemWriter<State> writer = new StaxEventItemWriterBuilder<State>()
                .name("stateWriter")
                .resource(new FileSystemResource(fileOutputPath))
                .marshaller(marshaller)
                .rootTagName("EnrichedStates")
                .build();

        SynchronizedItemStreamWriter<State> synchronizedWriter = new SynchronizedItemStreamWriter<>();
        synchronizedWriter.setDelegate(writer);

        return synchronizedWriter;
    }

    @Bean(name = "stateJob")
    @Primary
    public Job stateGeneration() {
        return new JobBuilder("stateJob", jobRepository)
                .start(stateGenerationStep())
                .listener(new MainJobListener())
                .build();
    }


    @Bean
    public AsyncTaskExecutor stateTaskExecutor() {
        return new SimpleAsyncTaskExecutor();
    }
}
