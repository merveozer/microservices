package com.ozer.microservices.product_service.service;

import com.ozer.microservices.product_service.dto.ProductRequest;
import com.ozer.microservices.product_service.dto.ProductResponse;
import com.ozer.microservices.product_service.event.ProductPlacedEvent;
import com.ozer.microservices.product_service.model.Product;
import com.ozer.microservices.product_service.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {
    private final ProductRepository productRepository;
    private final KafkaTemplate<String, ProductPlacedEvent> kafkaTemplate;

    public ProductResponse createProduct(ProductRequest productRequest) {
        Product product = Product.builder()
                .name(productRequest.name())
                .description(productRequest.description())
                .price(productRequest.price())
                .build();
        productRepository.save(product);
        log.info("Product created successfully");
        ProductPlacedEvent productPlacedEvent = new ProductPlacedEvent(product.getName(), product.getDescription(), product.getPrice());
        log.info("Start - Sending ProductPlacedEvent {} to Kafka topic product-created", productPlacedEvent);
        kafkaTemplate.send("product-created", productPlacedEvent);
        log.info("Product created successfully");
        return new ProductResponse(product.getId(), product.getName(), product.getDescription(), product.getPrice());
    }

    public List<ProductResponse> getAllProducts() {
        return productRepository.findAll()
                .stream().map(product -> new ProductResponse(product.getId(), product.getName(), product.getDescription(), product.getPrice()))
                .toList();
    }
}
