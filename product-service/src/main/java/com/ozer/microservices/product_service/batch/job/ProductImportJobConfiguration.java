package com.ozer.microservices.product_service.batch.job;

import com.ozer.microservices.product_service.service.ProductService;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ProductImportJobConfiguration {

    @Bean
    public Job job(JobRepository jobRepository, ProductService productService) {
        return new ProductImportJob(jobRepository, productService);
    }
}
