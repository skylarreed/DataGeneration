package com.sr.datagen.jobs;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class AllGenerationJob {

    private Step userGenerationStep;
    private Step merchantGenerationStep;
    private Step cardGenerationStep;
    private Step transactionGenerationStep;
    private PlatformTransactionManager transactionManager;
    private JobRepository jobRepository;

    public AllGenerationJob(@Qualifier("userGenStep") Step userGenerationStep,
                            @Qualifier("merchantGenStep") Step merchantGenerationStep,
                            @Qualifier("cardGenStep") Step cardGenerationStep,
                            @Qualifier("transactionGenStep") Step transactionGenerationStep,
                            PlatformTransactionManager transactionManager,
                            JobRepository jobRepository) {
        this.userGenerationStep = userGenerationStep;
        this.merchantGenerationStep = merchantGenerationStep;
        this.cardGenerationStep = cardGenerationStep;
        this.transactionGenerationStep = transactionGenerationStep;
        this.transactionManager = transactionManager;
        this.jobRepository = jobRepository;
    }

    @Bean(name = "allGenJob")
    public Job allGenerationJob() {
        return new JobBuilder("allGenerationJob", jobRepository)
                .start(userGenerationStep)
                .next(merchantGenerationStep)
                .next(cardGenerationStep)
                .next(transactionGenerationStep)
                .build();
    }

}
