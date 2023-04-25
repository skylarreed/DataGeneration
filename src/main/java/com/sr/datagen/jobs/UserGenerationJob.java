package com.sr.datagen.jobs;

import com.sr.datagen.listeners.MainChunkListener;
import com.sr.datagen.listeners.MainJobListener;
import com.sr.datagen.models.Transaction;
import com.sr.datagen.models.User;
import com.sr.datagen.processors.UserProcessor;
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
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.oxm.xstream.XStreamMarshaller;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.transaction.PlatformTransactionManager;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class UserGenerationJob {

    private final PlatformTransactionManager transactionManager;

    private final JobRepository jobRepository;

    private final SynchronizedItemStreamReader<Transaction> reader;

    private final SynchronizedItemStreamWriter<User> writer;

    private final ListTransactionReader listTransactionReader;

    private final UserProcessor userProcessor;

    private final JpaItemWriter<User> userWriter;

    private final ThreadPoolTaskExecutor taskExecutor;


    @Autowired
    public UserGenerationJob(PlatformTransactionManager transactionManager, JobRepository jobRepository,
                             SynchronizedItemStreamReader<Transaction> reader,
                             SynchronizedItemStreamWriter<User> writer,
                             ListTransactionReader listTransactionReader, UserProcessor userProcessor, JpaItemWriter<User> userWriter,
                             @Qualifier("threadPoolTaskExecutor") ThreadPoolTaskExecutor taskExecutor) {
        this.transactionManager = transactionManager;
        this.jobRepository = jobRepository;
        this.reader = reader;
        this.writer = writer;
        this.listTransactionReader = listTransactionReader;
        this.userProcessor = userProcessor;
        this.userWriter = userWriter;
        this.taskExecutor = taskExecutor;
    }




    @Bean(destroyMethod = "", name = "userWriter")
    @StepScope
    public SynchronizedItemStreamWriter<User> xmlWriter(@Value("#{jobParameters[fileOutputPath]}") String fileOutputPath) {

        XStreamMarshaller marshaller = new XStreamMarshaller();
        Map<String, Class> aliases = new HashMap<>();
        aliases.put("BankUser", User.class);
        marshaller.setAliases(aliases);


        StaxEventItemWriter<User> writer = new StaxEventItemWriterBuilder<User>()
                .name("xmlWriter")
                .resource(new FileSystemResource(fileOutputPath))
                .marshaller(marshaller)
                .rootTagName("Users")
                .build();

        SynchronizedItemStreamWriter<User> synchronizedWriter = new SynchronizedItemStreamWriter<>();
        synchronizedWriter.setDelegate(writer);

        return synchronizedWriter;
    }



    @Bean(name = "userGenStep")
    public Step userStep(JobRepository jobRepo, PlatformTransactionManager transactionManager) {
        return new StepBuilder("userStep", jobRepo)
                .<Transaction, User>chunk(10000, transactionManager)
                .reader(reader)
                .processor(userProcessor)
                .writer(userWriter)
//                .listener(new StepExecutionListener() {
//                    @Override
//                    public void beforeStep(StepExecution stepExecution) {
//                        System.out.println("Starting user generation step");
//                        userProcessor.clearMap();
//                    }
//
//                    @Override
//                    public ExitStatus afterStep(StepExecution stepExecution) {
//                        System.out.println("Finished user generation step");
//                        return null;
//                    }
//                })
                .taskExecutor(taskExecutor)
                .listener(new MainChunkListener())
                .build();
    }


    @Bean(name = "usersJob")
    @Primary
    public Job usersJob(@Qualifier("userGenStep") Step userStep) {
        return new JobBuilder("usersJob", jobRepository)
                .start(userStep)
                .listener(new MainJobListener())
                .build();
    }


}
