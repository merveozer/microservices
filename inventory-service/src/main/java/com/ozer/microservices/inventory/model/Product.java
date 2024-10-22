package com.ozer.microservices.inventory.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.math.BigDecimal;
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Product {
    private String name;
    private String description;
    private BigDecimal price;
}