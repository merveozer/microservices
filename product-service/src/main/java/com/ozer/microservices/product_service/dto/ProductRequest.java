package com.ozer.microservices.product_service.dto;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record ProductRequest(String id, String name, String description, BigDecimal price) {
}
