package com.ozer.microservices.product_service;

import com.ozer.microservices.product_service.dto.ProductRequest;
import com.ozer.microservices.product_service.dto.ProductResponse;
import com.ozer.microservices.product_service.event.ProductPlacedEvent;
import com.ozer.microservices.product_service.model.Product;
import com.ozer.microservices.product_service.repository.ProductRepository;
import com.ozer.microservices.product_service.service.ProductService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.kafka.core.KafkaTemplate;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private KafkaTemplate<String, ProductPlacedEvent> kafkaTemplate;

    @InjectMocks
    private ProductService productService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createProduct_shouldSaveProductAndSendEvent() {
        ProductRequest productRequest = new ProductRequest("1", "Product Name", "Product Description", new BigDecimal("1"));

        Product product = Product.builder()
                .id(productRequest.id())
                .name(productRequest.name())
                .description(productRequest.description())
                .price(productRequest.price())
                .build();

        when(productRepository.save(any(Product.class))).thenReturn(product);

        ProductResponse response = productService.createProduct(productRequest);
        verify(productRepository, times(1)).save(any(Product.class));
        verify(kafkaTemplate, times(1)).send(eq("product-created"), any(ProductPlacedEvent.class));

        Assertions.assertEquals(product.getName(), response.name());
        Assertions.assertEquals(product.getDescription(), response.description());
        Assertions.assertEquals(product.getPrice(), response.price());
    }
}
