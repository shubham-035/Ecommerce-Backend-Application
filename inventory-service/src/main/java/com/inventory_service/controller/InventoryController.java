package com.inventory_service.controller;

import com.inventory_service.dto.InventoryRequest;
import com.inventory_service.dto.InventoryRollbackRequest;
import com.inventory_service.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/inventory")
@RequiredArgsConstructor
public class InventoryController {
    private final InventoryService inventoryService;

    @PostMapping("/reserve")
    public ResponseEntity<String> reserveInventory(@RequestBody List<InventoryRequest> requests){
        inventoryService.reservedInventroy(requests);
        return ResponseEntity.ok("Inventory Reserved");
    }

    @PostMapping("/rollback")
    public ResponseEntity<String> rollbackInventory(@RequestBody List<InventoryRollbackRequest> requests){
        inventoryService.rollBackInventory(requests);
        return ResponseEntity.ok("Inventory Rollback");
    }

}
