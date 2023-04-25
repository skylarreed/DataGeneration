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
public class GenerateMerchantsController {
    private final JobLauncherService jobLauncherService;
    @Autowired
    public GenerateMerchantsController(JobLauncherService jobLauncherService) {
        this.jobLauncherService = jobLauncherService;
    }

    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Generates merchants from a file")
    @PostMapping("/merchant-gen")
    public ResponseEntity<?> generateMerchants(@RequestParam("filePath") String filePath) {
        return jobLauncherService.generateMerchant(filePath);
    }

}
