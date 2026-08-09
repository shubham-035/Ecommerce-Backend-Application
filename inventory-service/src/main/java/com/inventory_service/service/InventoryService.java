package com.inventory_service.service;

import com.inventory_service.dto.InventoryRequest;
import com.inventory_service.dto.InventoryRollbackRequest;
import com.inventory_service.entity.Inventory;
import com.inventory_service.repository.InventoryRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InventoryService {
    private final InventoryRepository inventoryRepository;

    @Transactional
    public void reservedInventroy(List<InventoryRequest> requests){
        for(InventoryRequest req:requests){
            Inventory inventory = inventoryRepository.findByProductId(req.getProductId())
                    .orElseThrow(() -> new RuntimeException("product not found"));
            if(inventory.getAvailableQuantity()<req.getQuantity()){
                throw new RuntimeException("insufficent quntity");
            }
            inventory.setAvailableQuantity(inventory.getAvailableQuantity()-req.getQuantity());

            inventoryRepository.save(inventory);
        }
    }

    @Transactional
    public void rollBackInventory(List<InventoryRollbackRequest> requests){
        for (InventoryRollbackRequest req:requests){
            Inventory inventory = inventoryRepository.findByProductId(req.getProductId()).orElseThrow();

            inventory.setAvailableQuantity(inventory.getAvailableQuantity()+req.getQuntity());

            inventoryRepository.save(inventory);
        }
    }
}
