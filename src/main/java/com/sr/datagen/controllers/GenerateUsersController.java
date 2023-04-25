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
public class GenerateUsersController {


    private final JobLauncherService jobLauncherService;
    @Autowired
    public GenerateUsersController(JobLauncherService jobLauncherService) {
        this.jobLauncherService = jobLauncherService;
    }

    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Generate users from a file")
    @PostMapping("/user-gen")
    public ResponseEntity<?> generateUsers(@RequestParam("filePath") String filePath) {
        return jobLauncherService.generateUser(filePath);
    }

    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Generate users from a file")
    @PostMapping("/gen-all")
    public ResponseEntity<?> generatell(@RequestParam("filePath") String filePath) {
        return jobLauncherService.generateAll(filePath);
    }
}
