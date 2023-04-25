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
public class GenerateStatesController {


    private final JobLauncherService jobLauncherService;
    @Autowired
    public GenerateStatesController(JobLauncherService jobLauncherService) {
        this.jobLauncherService = jobLauncherService;
    }

    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Generate states from a file")
    @PostMapping("/state-gen")
    public ResponseEntity<?> generateStates(@RequestParam("filePath") String filePath) {
        return jobLauncherService.generateState(filePath);
    }

}
