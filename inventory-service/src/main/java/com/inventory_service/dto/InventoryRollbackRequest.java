package com.inventory_service.dto;

import lombok.Data;

@Data
public class InventoryRollbackRequest {
    private long productId;
    private Integer quntity;

}
