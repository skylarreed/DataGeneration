package com.sr.datagen.controllers;

import com.sr.datagen.services.JobLauncherService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GenerateTransactionsController {
    private final JobLauncherService jobLauncherService;

    public GenerateTransactionsController(JobLauncherService jobLauncherService) {
        this.jobLauncherService = jobLauncherService;
    }

    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Generates transactions and pushes them to the database")
    @PostMapping("/transaction-gen")
    public ResponseEntity<?> generateTransactions(@RequestParam("filePath") String filePath) {
        return jobLauncherService.generateTransactions(filePath);
    }
}
