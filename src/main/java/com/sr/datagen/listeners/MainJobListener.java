package com.sr.datagen.listeners;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.JobExecutionListener;

@Slf4j(topic = "MainJobListener")
public class MainJobListener implements JobExecutionListener {
    @Override
    public void beforeJob(org.springframework.batch.core.JobExecution jobExecution) {
        log.info("Starting job: " + jobExecution.getJobInstance().getJobName());
    }

    @Override
    public void afterJob(org.springframework.batch.core.JobExecution jobExecution) {

        log.info("Total Records read: " + jobExecution.getStepExecutions()
                .stream()
                .mapToInt(stepExecution -> Math.toIntExact(stepExecution.getReadCount())).sum());

        log.info("Total Records written: " + jobExecution.getStepExecutions()
                .stream()
                .mapToInt(stepExecution -> Math.toIntExact(stepExecution.getWriteCount())).sum());

        log.info("Finished job: " + jobExecution.getJobInstance().getJobName());
        log.info("Job status: " + jobExecution.getStatus());

        if(jobExecution.getStatus().toString().equals("FAILED")) {
            log.error("Job failed: " + jobExecution.getJobInstance().getJobName());
        }
    }
}
