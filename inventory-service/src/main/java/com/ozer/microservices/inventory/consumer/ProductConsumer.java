package com.ozer.microservices.inventory.consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ozer.microservices.inventory.model.Inventory;
import com.ozer.microservices.inventory.model.Product;
import com.ozer.microservices.inventory.repository.InventoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductConsumer {
    private final InventoryRepository inventoryRepository;
    @Autowired
    private ObjectMapper objectMapper;
    @KafkaListener(topics = "product-created", groupId = "inventory-application-group")
    public void consume(String message) throws JsonProcessingException {
        Product product = objectMapper.readValue(message, Product.class);
        System.out.println("Received message: " + product);
        boolean isProductExist = inventoryRepository.existsBySkuCode(product.getName());
        if(isProductExist){
            inventoryRepository.updateQuantityByName(product.getName());
        }else{
            Inventory inventory = new Inventory();
            inventory.setSkuCode(product.getName());
            inventory.setQuantity(1);
            inventoryRepository.save(inventory);
        }
    }
}
