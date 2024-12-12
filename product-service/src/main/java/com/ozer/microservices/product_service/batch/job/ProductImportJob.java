package com.ozer.microservices.product_service.batch.job;

import com.ozer.microservices.product_service.batch.reader.JsonFileItemReader;
import com.ozer.microservices.product_service.model.ProductItem;
import com.ozer.microservices.product_service.dto.ProductRequest;
import com.ozer.microservices.product_service.service.ProductService;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.repository.JobRepository;

public class ProductImportJob implements Job {
    private final JobRepository jobRepository;
    private ProductService productService;

    public ProductImportJob(JobRepository jobRepository, ProductService productService) {
        this.jobRepository = jobRepository;
        this.productService = productService;
    }

    @Override
    public String getName() {
        return "ProductImportJob";
    }

    @Override
    public void execute(JobExecution execution) {
        try {
            JsonFileItemReader<ProductItem> reader = new JsonFileItemReader<>("ProductInput.json", ProductItem.class);
            System.out.println("Processing ProductImportJob information");
            while (reader.hasNext()) {
                ProductItem product = reader.next();
                ProductRequest productRequestService = ProductRequest.builder()
                        .name(product.getName())
                        .description(product.getDescription())
                        .price(product.getPrice())
                        .build();
                System.out.println("Processing product: " + product);
                productService.createProduct(productRequestService);
            }


            execution.setStatus(BatchStatus.COMPLETED);
            execution.setExitStatus(ExitStatus.COMPLETED);
            this.jobRepository.update(execution);
        } catch (Exception e) {
            execution.setStatus(BatchStatus.FAILED);
            execution.setExitStatus(new ExitStatus("FAILED", e.getMessage()));
            this.jobRepository.update(execution);
            throw new RuntimeException("Error processing ProductImportJob", e);
        }
    }
}
