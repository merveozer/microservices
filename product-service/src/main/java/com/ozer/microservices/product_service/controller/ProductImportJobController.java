package com.ozer.microservices.product_service.controller;

import com.ozer.microservices.product_service.batch.job.ProductImportJob;
import com.ozer.microservices.product_service.service.ProductService;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/job")
public class ProductImportJobController {

    private final JobLauncher jobLauncher;
    private final Job productImportJob;

    public ProductImportJobController(JobLauncher jobLauncher, JobRepository jobRepository, ProductService productService) {
        this.jobLauncher = jobLauncher;
        this.productImportJob = new ProductImportJob(jobRepository, productService);
    }

    @PostMapping("/product-import")
    public String triggerProductImportJob() {
        try {
            JobExecution execution = jobLauncher.run(productImportJob, new JobParameters());
            return "Job triggered successfully! Job ID: " + execution.getId();
        } catch (Exception e) {
            return "Job execution failed: " + e.getMessage();
        }
    }

}
