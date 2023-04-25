package com.sr.datagen.services;

import com.sr.datagen.exceptions.FileNotValidException;
import com.sr.datagen.models.Transaction;
import com.sr.datagen.readers.ListTransactionReader;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.*;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j(topic = "JobLauncherService")
@Service
public class JobLauncherService {



    private final Job userJob;
    private final Job merchantJob;
    private final Job cardJob;

    private final Job stateJob;

    private final Job transactionJob;

    private final Job allJob;

    private final JobLauncher jobLauncher;

    private JobExecution jobExecution;

    public JobLauncherService(@Qualifier("usersJob") Job userJob, @Qualifier("merchantJob") Job merchantJob,
                              @Qualifier("cardJob") Job cardJob, @Qualifier("stateJob") Job stateJob,
                              @Qualifier("transactionGenerationJob") Job transactionJob,
                              @Qualifier("allGenJob") Job allJob,
                              @Qualifier("asyncLauncher") JobLauncher jobLauncher) {
        this.userJob = userJob;
        this.merchantJob = merchantJob;
        this.cardJob = cardJob;
        this.transactionJob = transactionJob;
        this.allJob = allJob;
        this.jobLauncher = jobLauncher;
        this.stateJob = stateJob;
    }

    public ResponseEntity<?> generateUser(String filePath) {
        log.info("Received request to generate users");
        if(checkFile(filePath)) {
            return ResponseEntity.status(400).body("File not found");
        }
        if(jobExecution != null && jobExecution.isRunning()) {
            return ResponseEntity.status(400).body("Job is already running");
        }
        try {
            JobParameters jobParameters = new JobParametersBuilder()
                    .addLong("time", System.currentTimeMillis())
                    .addString("filePath", filePath)
                    .addString("fileOutputPath", "output/users_output.xml")
                    .toJobParameters();
            jobExecution =  jobLauncher.run(userJob, jobParameters);
            if(jobExecution.getStatus().isUnsuccessful()) {
                return ResponseEntity.status(500).body("Job failed");
            }
        } catch (FileNotValidException | JobExecutionAlreadyRunningException | JobRestartException |
                JobInstanceAlreadyCompleteException | JobParametersInvalidException e) {
            return ResponseEntity.status(500).body("Something went wrong, message: " + e.getMessage());
        }

        return ResponseEntity.status(201).body("Job Started Successfully");
    }

    public ResponseEntity<?> generateCard(String filePath) {
        log.info("Received request to generate cards");
        if(checkFile(filePath)) {
            return ResponseEntity.status(400).body("File not found");
        }
        if(jobExecution != null && jobExecution.isRunning()) {
            return ResponseEntity.status(400).body("Job is already running");
        }
        try {
            JobParameters jobParameters = new JobParametersBuilder()
                    .addLong("time", System.currentTimeMillis())
                    .addString("filePath", filePath)
                    .addString("fileOutputPath", "output/card_output.xml")
                    .toJobParameters();
            jobExecution =  jobLauncher.run(cardJob, jobParameters);
            if(jobExecution.getStatus().isUnsuccessful()) {
                return ResponseEntity.status(500).body("Job failed");
            }
        } catch (FileNotValidException | JobExecutionAlreadyRunningException | JobRestartException |
                 JobInstanceAlreadyCompleteException | JobParametersInvalidException e) {
            return ResponseEntity.status(500).body("Something went wrong, message: " + e.getMessage());
        }

        return ResponseEntity.status(201).body("Job Started Successfully");
    }

    public ResponseEntity<?> generateMerchant(String filePath){
        log.info("Received request to generate merchants");
        if(checkFile(filePath))
            return ResponseEntity.status(400).body("File not found");

        if(jobExecution != null && jobExecution.isRunning()) {
            return ResponseEntity.status(400).body("Job is already running");
        }
        try {
            JobParameters jobParameters = new JobParametersBuilder()
                    .addLong("time", System.currentTimeMillis())
                    .addString("filePath", filePath)
                    .addString("fileOutputPath", "output/merchant_output.xml")
                    .toJobParameters();
            jobExecution =  jobLauncher.run(merchantJob, jobParameters);
            if(jobExecution.getStatus().isUnsuccessful()) {
                return ResponseEntity.status(500).body("Job failed, make sure the file path is correct");
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Something went wrong, message: " + e.getMessage());
        }

        return ResponseEntity.status(201).body("Job Started Successfully");
    }

    public ResponseEntity<?> generateState(String filePath){
        log.info("Received request to generate states");
        if(checkFile(filePath))
            return ResponseEntity.status(400).body("Please use a proper file path");

        if(jobExecution != null && jobExecution.isRunning()) {
            return ResponseEntity.status(400).body("Job is already running");
        }
        try {
            JobParameters jobParameters = new JobParametersBuilder()
                    .addLong("time", System.currentTimeMillis())
                    .addString("filePath", filePath)
                    .addString("fileOutputPath", "output/state_output.xml")
                    .toJobParameters();
            jobExecution =  jobLauncher.run(stateJob, jobParameters);
            if(jobExecution.getStatus().isUnsuccessful()) {
                return ResponseEntity.status(500).body("Job failed to execute. Make sure the file path is correct");
            }
        } catch (FileNotValidException | JobExecutionAlreadyRunningException | JobRestartException |
                 JobInstanceAlreadyCompleteException | JobParametersInvalidException e) {
            return ResponseEntity.status(500).body("Error... Unable to launch job: " + e.getMessage());
        }

        return ResponseEntity.status(201).body("Job Started Successfully");
    }


    public ResponseEntity<?> generateTransactions(String filePath){
        log.info("Received request to generate transactions");
        if(checkFile(filePath))
            return ResponseEntity.status(400).body("Please use a proper file path");

        if(jobExecution != null && jobExecution.isRunning()) {
            return ResponseEntity.status(400).body("Job is already running");
        }
        try {
            JobParameters jobParameters = new JobParametersBuilder()
                    .addLong("time", System.currentTimeMillis())
                    .addString("filePath", filePath)
                    .toJobParameters();
            jobExecution =  jobLauncher.run(transactionJob, jobParameters);
            if(jobExecution.getStatus().isUnsuccessful()) {
                return ResponseEntity.status(500).body("Job failed to execute. Make sure the file path is correct");
            }
        } catch (FileNotValidException | JobExecutionAlreadyRunningException | JobRestartException |
                 JobInstanceAlreadyCompleteException | JobParametersInvalidException e) {
            return ResponseEntity.status(500).body("Error... Unable to launch job: " + e.getMessage());
        }

        return ResponseEntity.status(201).body("Job Started Successfully");
    }
    public ResponseEntity<?> generateAll(String filePath){
        log.info("Received request to generate all fields");
        if(checkFile(filePath))
            return ResponseEntity.status(400).body("Please use a proper file path");

        if(jobExecution != null && jobExecution.isRunning()) {
            return ResponseEntity.status(400).body("Job is already running");
        }
        try {
            JobParameters jobParameters = new JobParametersBuilder()
                    .addLong("time", System.currentTimeMillis())
                    .addString("filePath", filePath)
                    .toJobParameters();
            jobExecution =  jobLauncher.run(allJob, jobParameters);
            if(jobExecution.getStatus().isUnsuccessful()) {
                return ResponseEntity.status(500).body("Job failed to execute. Make sure the file path is correct");
            }
        } catch (FileNotValidException | JobExecutionAlreadyRunningException | JobRestartException |
                 JobInstanceAlreadyCompleteException | JobParametersInvalidException e) {
            return ResponseEntity.status(500).body("Error... Unable to launch job: " + e.getMessage());
        }

        return ResponseEntity.status(201).body("Job Started Successfully");
    }

    public boolean checkFile(String filePath) {
        if(filePath.isEmpty()) {
            return true;
        }
        Resource resource = new FileSystemResource(filePath);
        return !resource.exists();
    }

}
