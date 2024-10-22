package com.ozer.microservices.inventory.repository;

import com.ozer.microservices.inventory.model.Inventory;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface InventoryRepository extends JpaRepository<Inventory, Long> {
    boolean existsBySkuCodeAndQuantityIsGreaterThanEqual(String skuCode, Integer quantity);
    boolean existsBySkuCode(String skuCode);
    @Transactional
    @Modifying
    @Query("UPDATE Inventory i SET i.quantity = i.quantity + 1 WHERE i.skuCode = :name")
    void updateQuantityByName(String name);
}
