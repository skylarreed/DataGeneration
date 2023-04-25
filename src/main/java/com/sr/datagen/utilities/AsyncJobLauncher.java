package com.sr.datagen.utilities;

import com.sr.datagen.services.JobLauncherService;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.support.TaskExecutorJobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.stereotype.Component;

@Component
public class AsyncJobLauncher {

    private JobRepository jobRepository;

    public AsyncJobLauncher(JobRepository jobRepository) {
        this.jobRepository = jobRepository;
    }
    @Bean(name = "asyncLauncher")
    public JobLauncher customJobLauncher() {
        TaskExecutorJobLauncher jobLauncher = new TaskExecutorJobLauncher();
        jobLauncher.setJobRepository(jobRepository);
        jobLauncher.setTaskExecutor(new SimpleAsyncTaskExecutor());
        return jobLauncher;
    }
}
