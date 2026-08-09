package com.order_service.client;

import com.order_service.dto.InventoryRequest;
import com.order_service.dto.InventoryRollbackRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name="inventory-service" , url = "http://localhost:8087/api/v1/inventory")
public interface InventoryClient {

    @PostMapping("/reserve")
    public ResponseEntity<String> reserveInventory(@RequestBody List<InventoryRequest> requests);

    @PostMapping("/rollback")
    public ResponseEntity<String> rollbackInventory(@RequestBody List<InventoryRollbackRequest> requests);

}
