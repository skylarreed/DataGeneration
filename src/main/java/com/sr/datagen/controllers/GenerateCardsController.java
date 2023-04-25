package com.sr.datagen.controllers;

import com.sr.datagen.models.Transaction;
import com.sr.datagen.services.JobLauncherService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/job")
public class GenerateCardsController {

    private final JobLauncherService jobLauncherService;

    @Autowired
    public GenerateCardsController(JobLauncherService jobLauncherService) {
        this.jobLauncherService = jobLauncherService;
    }

    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Generate cards from a file")
    @PostMapping("/card-gen")
    public ResponseEntity<?> generateCards(@RequestParam("filePath") String filePath) {
        return jobLauncherService.generateCard(filePath);
    }


    @GetMapping("/health")
    public String health() {
        return "OK";
    }


}
