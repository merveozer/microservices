package com.ozer.microservices.product_service.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductPlacedEvent {
    private String name;
    private String description;
    private BigDecimal price;
}
